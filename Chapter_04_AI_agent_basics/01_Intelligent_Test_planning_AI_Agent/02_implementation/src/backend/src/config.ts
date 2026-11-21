import fs from "node:fs";
import path from "node:path";
import dotenv from "dotenv";

let envDir = process.cwd();
for (let i = 0; i < 8; i++) {
  const candidate = path.join(envDir, ".env");
  if (fs.existsSync(candidate)) {
    dotenv.config({ path: candidate });
    break;
  }
  envDir = path.dirname(envDir);
}

export const config = {
  port: Number(process.env.PORT || 3001),
  corsOrigin: process.env.CORS_ORIGIN || "http://localhost:5173",
  jiraUrl: process.env.JIRA_URL || "",
  jiraEmail: process.env.JIRA_EMAIL_ID || "",
  jiraToken: process.env.JIRA_API_TOKEN || "",
  groqKey: process.env.GROQ_API_KEY || ""
};
