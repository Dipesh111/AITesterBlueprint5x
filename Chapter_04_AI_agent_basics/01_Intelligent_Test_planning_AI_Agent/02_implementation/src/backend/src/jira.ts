import { config } from "./config.js";
import type { JiraTicket } from "./types.js";

function text(value: unknown): string {
  if (!value) return "";
  if (typeof value === "string") return value;
  if (Array.isArray(value)) return value.map(text).filter(Boolean).join("\n");
  if (typeof value === "object") {
    const item = value as Record<string, unknown>;
    if (typeof item.text === "string") return item.text;
    if (item.content) return text(item.content);
  }
  return "";
}

export async function fetchJiraTicket(key: string): Promise<JiraTicket> {
  if (!config.jiraUrl || !config.jiraEmail || !config.jiraToken) {
    throw new Error("Insufficient information to determine.");
  }
  const url = `${config.jiraUrl.replace(/\/$/, "")}/rest/api/3/issue/${encodeURIComponent(key)}?fields=summary,description,issuetype,priority,status,assignee,reporter,labels,components,comment,attachment`;
  const response = await fetch(url, {
    headers: {
      Authorization: `Basic ${Buffer.from(`${config.jiraEmail}:${config.jiraToken}`).toString("base64")}`,
      Accept: "application/json"
    }
  });
  if (!response.ok) throw new Error(response.status === 404 ? "Jira ticket not found." : `Jira request failed (${response.status}).`);
  const data = await response.json() as Record<string, any>;
  const fields = data.fields || {};
  const description = text(fields.description);
  const acMatch = description.match(/(?:acceptance criteria|acceptance criteria:)[\s\S]*$/i);
  return {
    key: data.key,
    summary: fields.summary || "",
    description,
    issueType: fields.issuetype?.name || "",
    priority: fields.priority?.name || "",
    status: fields.status?.name || "",
    assignee: fields.assignee?.displayName || "",
    reporter: fields.reporter?.displayName || "",
    acceptanceCriteria: acMatch ? text(acMatch[0]) : "",
    labels: fields.labels || [],
    components: (fields.components || []).map((item: { name?: string }) => item.name || ""),
    comments: (fields.comment?.comments || []).map((item: { body?: unknown }) => text(item.body)).filter(Boolean),
    attachments: (fields.attachment || []).map((item: { filename?: string }) => item.filename || "")
  };
}
