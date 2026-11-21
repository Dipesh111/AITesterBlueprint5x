import express from "express";
import cors from "cors";
import { config } from "./config.js";
import { fetchJiraTicket } from "./jira.js";
import { generatePlan } from "./generation.js";

const app = express();
app.use(cors({ origin: config.corsOrigin }));
app.use(express.json({ limit: "1mb" }));

app.get("/api/health", (_req, res) => {
  res.json({ ok: true });
});

app.get("/api/config/status", (_req, res) => {
  res.json({
    jiraConfigured: Boolean(config.jiraUrl && config.jiraEmail && config.jiraToken),
    aiConfigured: Boolean(config.groqKey)
  });
});

app.post("/api/tickets/analyze", async (req, res) => {
  const key = typeof req.body?.key === "string" ? req.body.key.trim().toUpperCase() : "";
  if (!/^[A-Z][A-Z0-9]*-[0-9]+$/.test(key)) {
    res.status(400).json({ error: "Enter a valid Jira ticket ID, such as PROJ-1234." });
    return;
  }
  try {
    const ticket = await fetchJiraTicket(key);
    const plan = await generatePlan(ticket);
    res.json({ ticket, plan });
  } catch (error) {
    const message = error instanceof Error ? error.message : "Insufficient information to determine.";
    res.status(message.includes("Insufficient") ? 500 : 502).json({ error: message });
  }
});

export default app;
