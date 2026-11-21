# AI-Powered Jira Test Plan Creator

## Local development

From `02_implementation`:

```powershell
npm install --prefix src/backend
npm install --prefix src/frontend
npm run dev --prefix src/backend
npm run dev --prefix src/frontend
```

Open `http://localhost:5173`.

The backend reads the existing chapter `.env` for local development. Credentials are only read by the backend and are never sent to the browser.

## Deploy to Vercel

1. Import this `02_implementation` folder as the Vercel project root (or set **Root Directory** to `Chapter_04_AI_agent_basics/01_Intelligent_Test_planning_AI_Agent/02_implementation` relative to the repository root) and name the project `ai5xtestplancreator`.
2. In **Project Settings → Environment Variables**, add `JIRA_EMAIL_ID`, `JIRA_API_TOKEN`, `JIRA_URL`, and `GROQ_API_KEY`. Set them for Production (and Preview if required). Never place secret values in source files or the browser UI.
3. Deploy with `npx vercel --prod`.

The React app is served as static output; health, configuration-status, and ticket-analysis APIs deploy as separate Vercel functions. Credential editing from the deployed UI is intentionally not supported: deployment environment variables must be managed in Vercel project settings.
