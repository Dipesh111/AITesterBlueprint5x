import { config } from "./config.js";
import type { JiraTicket, TestPlan } from "./types.js";

export function fallbackPlan(ticket: JiraTicket): TestPlan {
  const requirements = ticket.acceptanceCriteria
    ? ticket.acceptanceCriteria.split(/\n|;/).map((item) => item.replace(/^[-*\d.)\s]+/, "").trim()).filter(Boolean)
    : [];
  const scenarios = requirements.length
    ? requirements.map((requirement, index) => ({
        id: `TS-${index + 1}`,
        priority: "P0",
        type: "pos",
        scenario: `Verify: ${requirement}`,
        mapsTo: `AC-${index + 1}`
      }))
    : [{
        id: "TS-1",
        priority: "P0",
        type: "pos",
        scenario: "Insufficient information to determine.",
        mapsTo: "Missing acceptance criteria"
      }];
  return {
    title: `Test Plan — ${ticket.key}: ${ticket.summary}`,
    status: "DRAFT - pending human review",
    scope: [ticket.description || "Insufficient information to determine."],
    gaps: requirements.length ? [] : ["Acceptance criteria were not available."],
    scenarios,
    data: ["Insufficient information to determine."],
    risks: ["Requirements may be incomplete or ambiguous."],
    sourceText: ticket.description
  };
}

export async function generatePlan(ticket: JiraTicket): Promise<TestPlan> {
  if (!config.groqKey) return fallbackPlan(ticket);
  const prompt = `Create a test plan for this Jira ticket. Use only the provided fields. Return JSON with: title,status,scope,gaps,scenarios(id,priority,type,scenario,mapsTo),data,risks,sourceText. If missing, use exactly "Insufficient information to determine.". Label inferred details exactly "Inference (low confidence)". Ticket JSON: ${JSON.stringify(ticket)}`;
  try {
    const response = await fetch("https://api.groq.com/openai/v1/chat/completions", {
      method: "POST",
      headers: { Authorization: `Bearer ${config.groqKey}`, "Content-Type": "application/json" },
      body: JSON.stringify({
        model: "llama-3.3-70b-versatile",
        temperature: 0,
        response_format: { type: "json_object" },
        messages: [{ role: "system", content: "You are a QA test plan generator. Return only valid JSON." }, { role: "user", content: prompt }]
      })
    });
    if (!response.ok) return fallbackPlan(ticket);
    const data = await response.json() as { choices?: Array<{ message?: { content?: string } }> };
    const content = data.choices?.[0]?.message?.content;
    if (!content) return fallbackPlan(ticket);
    return JSON.parse(content) as TestPlan;
  } catch {
    return fallbackPlan(ticket);
  }
}
