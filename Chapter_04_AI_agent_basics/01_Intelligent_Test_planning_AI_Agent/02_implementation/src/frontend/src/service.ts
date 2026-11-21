import type { JiraTicket, TestPlan } from "./types";

export async function analyzeTicket(key: string): Promise<{ ticket: JiraTicket; plan: TestPlan }> {
  const response = await fetch("/api/tickets/analyze", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ key })
  });
  const data = await response.json();
  if (!response.ok) throw new Error(data.error || "Request failed.");
  return data;
}

export async function getConfigStatus(): Promise<{ jiraConfigured: boolean; aiConfigured: boolean }> {
  const response = await fetch("/api/config/status");
  if (!response.ok) throw new Error("Could not read configuration status.");
  return response.json();
}
