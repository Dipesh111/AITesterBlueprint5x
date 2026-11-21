import { FormEvent, useEffect, useState } from "react";
import { analyzeTicket, getConfigStatus } from "./service";
import type { JiraTicket, TestPlan } from "./types";

export default function App() {
  const [settings, setSettings] = useState(false);
  const [key, setKey] = useState("");
  const [ticket, setTicket] = useState<JiraTicket | null>(null);
  const [plan, setPlan] = useState<TestPlan | null>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [status, setStatus] = useState({ jiraConfigured: false, aiConfigured: false });

  useEffect(() => {
    getConfigStatus().then(setStatus).catch((item: Error) => setError(item.message));
  }, []);

  async function submit(event: FormEvent) {
    event.preventDefault();
    setError("");
    setLoading(true);
    try {
      const result = await analyzeTicket(key);
      setTicket(result.ticket);
      setPlan(result.plan);
    } catch (item) {
      setError(item instanceof Error ? item.message : "Insufficient information to determine.");
    } finally {
      setLoading(false);
    }
  }

  return (
    <main className="shell">
      <button className="settings-link" onClick={() => setSettings((value) => !value)}>
        {settings ? "Main" : "Settings"}
      </button>

      {!settings ? (
        <section className="panel main-panel">
          <h1>Main page</h1>
          <div className="reply-box">
            {loading && <p>Analyzing Jira ticket...</p>}
            {!loading && plan && (
              <article>
                <h2>{plan.title}</h2>
                <p className="draft">{plan.status}</p>
                <h3>1. Scope & Objectives</h3>
                {plan.scope.map((item, index) => <p key={index}>{item}</p>)}
                <h3>2. Gaps & Questions</h3>
                {plan.gaps.length ? plan.gaps.map((item, index) => <p key={index}>{item}</p>) : <p>None identified.</p>}
                <h3>3. Test Scenarios</h3>
                <table>
                  <thead><tr><th>ID</th><th>Priority</th><th>Type</th><th>Scenario</th><th>Maps to</th></tr></thead>
                  <tbody>{plan.scenarios.map((item) => (
                    <tr key={item.id}><td>{item.id}</td><td>{item.priority}</td><td>{item.type}</td><td>{item.scenario}</td><td>{item.mapsTo}</td></tr>
                  ))}</tbody>
                </table>
                <h3>4. Test Data & Environment</h3>
                {plan.data.map((item, index) => <p key={index}>{item}</p>)}
                <h3>5. Risks & Assumptions</h3>
                {plan.risks.map((item, index) => <p key={index}>{item}</p>)}
              </article>
            )}
            {!loading && !plan && <p>Generated test plan appears here.</p>}
          </div>
          {ticket && <p className="ticket-meta">{ticket.key} · {ticket.status} · {ticket.issueType}</p>}
          {error && <p className="error">{error}</p>}
          <form onSubmit={submit}>
            <input
              aria-label="Jira ticket"
              value={key}
              onChange={(event) => setKey(event.target.value)}
              placeholder="Prompt (JIRA Ticket)"
              disabled={loading}
            />
          </form>
        </section>
      ) : (
        <section className="panel settings-panel">
          <h1>Settings</h1>
          <div className="settings-card">
            <h2>JIRA Connection</h2>
            <p>{status.jiraConfigured ? "Configured" : "Insufficient information to determine."}</p>
            <p>{status.jiraConfigured ? "Email, URL, and token loaded securely" : "Check environment configuration"}</p>
          </div>
          <div className="settings-card">
            <h2>LLM Configuration</h2>
            <p>{status.aiConfigured ? "Groq configured" : "Insufficient information to determine."}</p>
          </div>
        </section>
      )}
    </main>
  );
}
