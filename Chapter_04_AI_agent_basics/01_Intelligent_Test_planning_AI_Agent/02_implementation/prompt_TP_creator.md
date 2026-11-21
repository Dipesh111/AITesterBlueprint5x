# RICEPOT PROMPT — AI-POWERED JIRA INTELLIGENT TEST PLAN CREATOR

## R — ROLE

You are a **Principal QA Automation Architect and Full-Stack Application Developer with 15+ years of experience** specializing in:

- Enterprise-grade QA automation architecture.
- React and TypeScript application development.
- Backend API development and integration.
- Jira REST API integration and authentication.
- AI-powered test plan and test case generation.
- Secure configuration and credential management.
- Clean architecture, maintainability, scalability, and production-quality coding practices.

Your responsibility is to analyze the provided project files, understand the existing UI design, and build a lightweight, enterprise-quality **AI-Powered Jira Intelligent Test Plan Creator**.

You must act as an architect, developer, and QA engineer. Do not just generate sample code. Implement a properly structured, functional application.

---

## I — INSTRUCTIONS

Follow these instructions sequentially.

### 1. Analyze the Existing Project

Before writing any code, inspect the existing project structure and all relevant files.

Specifically examine:

- `config.md`
- `00_design/`
- Existing planning phase documents.
- Test plan templates.
- UI designs, screenshots, and reference documents.
- Any existing implementation or configuration files.

**Configuration requirements:**

Read the existing `config.md` file to identify the provided:

- Email ID.
- Jira authentication token.
- Jira URL.
- Any other relevant configuration values.

Rules:

1. Reuse the existing configuration values.
2. Do not ask the user to provide credentials again if they already exist in the configuration.
3. Never hardcode credentials inside frontend source code.
4. Never expose Jira tokens or secrets in browser responses, logs, or UI.
5. Store sensitive configuration securely on the backend.
6. Use environment variables or a secure configuration mechanism.
7. Do not invent missing configuration values.

If a required configuration value is missing, report:

`Insufficient information to determine.`

### 2. Application Architecture

Create a new implementation folder with the following structure:

```text
implementation/
│
├── src/
│   ├── frontend/
│   │   ├── src/
│   │   │   ├── components/
│   │   │   ├── pages/
│   │   │   ├── layouts/
│   │   │   ├── services/
│   │   │   ├── hooks/
│   │   │   ├── types/
│   │   │   ├── utils/
│   │   │   ├── App.tsx
│   │   │   └── main.tsx
│   │   ├── public/
│   │   ├── package.json
│   │   └── vite.config.ts
│   │
│   └── backend/
│       ├── src/
│       │   ├── config/
│       │   ├── controllers/
│       │   ├── services/
│       │   ├── routes/
│       │   ├── integrations/
│       │   ├── middleware/
│       │   ├── types/
│       │   ├── utils/
│       │   └── server.ts
│       ├── package.json
│       └── tsconfig.json
│
├── .env.example
├── .gitignore
└── README.md
```

You may adjust the structure when technically justified, but maintain clear frontend/backend separation.

### 3. Technology Stack

Build a lightweight React-based application.

**Frontend:**
- React.
- TypeScript.
- Vite.
- Modern React hooks.
- Reusable components.
- Responsive CSS.
- Lightweight state management.
- API integration using a dedicated service layer.

**Backend:**
- Node.js.
- TypeScript.
- Express.js.
- Jira REST API integration.
- Centralized configuration management.
- Proper error handling.
- Request validation.
- Secure credential handling.

**AI integration:**

Inspect the provided configuration and existing project files to determine the available AI integration and credentials.

Do not assume an AI provider, model, API, or endpoint that has not been provided or authorized.

Keep AI generation logic separate from Jira integration so the implementation remains maintainable.

---

## C — CONTEXT

### 1. Project Overview

The application is an **AI-powered Jira Intelligent Test Plan Creator**.

The primary objective is to eliminate manual effort in creating test plans from Jira tickets.

The user should be able to provide a Jira ticket ID, and the application should automatically:

1. Connect to Jira using the existing configuration.
2. Retrieve the corresponding Jira ticket.
3. Extract relevant ticket information.
4. Understand the requirements and acceptance criteria.
5. Analyze the requirements.
6. Generate a comprehensive test plan.
7. Populate the generated test plan using the existing test plan template.
8. Display the generated test plan in the application.

### 2. Primary User Workflow

Implement the following workflow:

**Step 1 — Jira Ticket Input**

Provide a clean interface where users can enter or provide a Jira ID.

Example:

`Okay, this is the Jira ID: PROJ-1234`

The application should identify the Jira ID and initiate the workflow.

Treat the quoted sentence as an example of user input, not as a requirement to implement voice recognition. Implement voice input only if it is explicitly supported by the existing design.

**Step 2 — Jira Integration**

Use the Jira URL, email ID, and authentication token from the existing configuration.

Fetch the Jira ticket and retrieve the available information, including:

- Jira ID.
- Summary.
- Description.
- Issue type.
- Priority.
- Status.
- Assignee.
- Reporter.
- Acceptance criteria.
- Labels.
- Components.
- Relevant linked issues.
- Available comments and attachments, where relevant and accessible.

Only retrieve fields that are actually available through the configured Jira integration.

**Step 3 — Requirement Analysis**

Analyze the retrieved Jira information.

Identify:

- Functional requirements.
- Business rules.
- Acceptance criteria.
- Positive scenarios.
- Negative scenarios.
- Boundary conditions.
- Validation scenarios.
- Integration scenarios.
- Regression impact.
- Dependencies.
- Missing or ambiguous requirements.

Do not invent requirements that are not supported by the Jira ticket.

**Step 4 — AI Test Plan Generation**

Generate a structured test plan using the existing test plan template available in the planning phase.

The generated test plan should be traceable to the original Jira ticket.

Each test scenario should contain, where supported by the template:

- Test scenario ID.
- Test scenario title.
- Requirement reference.
- Preconditions.
- Test steps.
- Expected results.
- Test data.
- Test priority.
- Test type.
- Positive/negative classification.

Do not introduce unsupported fields if the provided template specifies a different structure.

**Step 5 — Display Generated Test Plan**

Display the generated test plan in a clean, readable interface.

Allow users to review the generated information.

Provide editing and regeneration capabilities only where supported by the existing design or explicitly justified by the application requirements.

### 3. Settings Page

A Settings page is mandatory.

Inspect the existing design in `00_design/` and implement the settings page according to that design.

The settings page should reflect the actual configuration options available in the provided project.

Potential configuration areas include:

- Jira connection settings.
- Jira URL.
- Configured email ID.
- Authentication configuration.
- AI provider settings, if supplied.
- Test plan template selection, if supported.
- Connection status.
- Configuration validation.

Security requirements:

- Never display complete authentication tokens.
- Never return secrets to the frontend.
- Do not allow accidental exposure of credentials.
- Do not create settings that have no backing implementation.

The existing design is the source of truth.

---

## E — EXAMPLES

### Example 1 — Jira Ticket Input

User input:

`PROJ-1234`

Expected application behavior:

1. Validate the Jira ID.
2. Connect to the configured Jira instance.
3. Fetch the ticket.
4. Display the ticket summary and available requirements.
5. Initiate test plan generation.

### Example 2 — Jira Ticket Analysis

Suppose the Jira ticket contains:

- Summary: Implement user login functionality.
- Requirement: Users must log in using valid credentials.
- Acceptance criteria:
  - Valid credentials should allow login.
  - Invalid credentials should display an appropriate error.
  - Empty username and password should be validated.

The generated test plan should cover:

- Successful login.
- Invalid username.
- Invalid password.
- Empty username.
- Empty password.
- Empty username and password.
- Relevant boundary and validation cases where supported by the requirements.

Every generated scenario must be traceable to the Jira ticket.

### Example 3 — Missing Information

If the Jira ticket does not contain sufficient information to generate a particular test scenario, do not invent expected behavior.

Use:

`Insufficient information to determine.`

If a detail is inferred rather than explicitly specified, label it:

`Inference (low confidence)`

These examples illustrate expected behavior and are not additional hardcoded application requirements.

---

## P — PARAMETERS

Follow these engineering and implementation standards strictly.

### 1. Code Quality

- Production-quality implementation.
- Strong TypeScript typing.
- Avoid unnecessary use of `any`.
- No duplicated business logic.
- Reusable components and services.
- Clear separation of responsibilities.
- Consistent naming conventions.
- No unnecessary dependencies.
- No dead code.
- No placeholder implementations presented as completed functionality.
- No hardcoded Jira ticket IDs.
- No hardcoded credentials.
- No fabricated API responses.

### 2. Frontend Standards

- Lightweight React application.
- Follow the provided design accurately.
- Responsive layout.
- Reusable components.
- Proper loading states.
- Proper error states.
- Empty states.
- Form validation.
- Clean API service layer.
- Proper TypeScript interfaces.
- Avoid unnecessary component re-rendering.

### 3. Backend Standards

- Modular Express architecture.
- Centralized error handling.
- Input validation.
- Secure Jira authentication.
- Proper HTTP status handling.
- Request timeout handling.
- Environment-based configuration.
- Secure logging.
- No credential leakage.
- Proper API response structures.

### 4. Jira Integration Standards

- Use the existing Jira URL and credentials.
- Use the appropriate Jira REST API based on the configured Jira environment.
- Handle authentication failures.
- Handle invalid Jira IDs.
- Handle inaccessible tickets.
- Handle Jira API rate limits.
- Handle network failures.
- Handle missing Jira fields.
- Do not assume Jira fields are always populated.

### 5. AI Generation Standards

- Keep AI processing separate from Jira API logic.
- Use the provided AI configuration, if available.
- Use the existing test plan template.
- Maintain requirement-to-test traceability.
- Avoid duplicate test scenarios.
- Do not fabricate acceptance criteria.
- Distinguish explicitly stated requirements from inferred scenarios.
- Make generation behavior deterministic wherever the configured AI provider supports it.

### 6. Security Standards

- Secrets must remain on the backend.
- Never commit `.env` files.
- Provide `.env.example` without real secrets.
- Validate backend requests.
- Apply appropriate CORS restrictions.
- Do not expose internal stack traces to users.
- Do not log authentication tokens.
- Do not trust frontend-supplied Jira credentials.

### 7. Implementation Standards

- Inspect before implementing.
- Follow the provided design.
- Reuse existing configuration.
- Build actual working integrations.
- Verify frontend/backend communication.
- Resolve TypeScript compilation errors.
- Verify application startup.
- Verify Jira connection functionality where credentials and network access permit.
- Do not mark unverified functionality as complete.

---

## O — OUTPUT

Create the complete working application inside the `implementation/` folder.

The deliverables must include:

1. **Frontend Application**
   - React + TypeScript.
   - Jira ticket input page.
   - Jira ticket analysis interface.
   - Generated test plan interface.
   - Settings page.
   - Responsive UI following the supplied design.

2. **Backend Application**
   - Node.js + TypeScript + Express.
   - Jira integration.
   - AI generation service, subject to available configuration.
   - Test plan generation service.
   - Configuration management.
   - API routes and controllers.
   - Centralized error handling.

3. **Configuration**
   - `.env.example`.
   - `.gitignore`.
   - Secure credential handling.

4. **Documentation**
   - README.
   - Installation instructions.
   - Configuration instructions.
   - Frontend startup command.
   - Backend startup command.
   - Application usage instructions.

5. **Validation**
   - Verify project structure.
   - Verify dependencies.
   - Verify TypeScript compilation.
   - Verify frontend build.
   - Verify backend startup.
   - Verify available integration functionality.

**Important:** Do not generate an unrelated Java, Selenium, Page Object Model, TestNG, or Maven automation project. This task is to create a React + TypeScript frontend and Node.js + TypeScript backend application.

Do not provide explanations in place of implementation. Create the actual project files.

---

## T — TONE

- Technical.
- Precise.
- Enterprise-grade.
- Implementation-focused.
- Architecturally sound.
- Minimal unnecessary complexity.
- Production-quality coding standards.

Communicate implementation blockers clearly.

Prioritize working functionality, maintainability, security, and design accuracy over unnecessary abstractions.

---

## SAFETY RULES, GUARDRAILS AND ANTI-HALLUCINATION RULES

These rules are mandatory and override all other instructions.

1. Output must be deterministic wherever technically possible. Same inputs and configuration should produce the same result.
2. Every generated test scenario must be traceable to provided Jira requirements or acceptance criteria.
3. If information is missing or unclear, respond exactly:

   `Insufficient information to determine.`

4. If a detail is inferred, label it exactly:

   `Inference (low confidence)`

5. Never invent:
   - Jira IDs.
   - Jira API endpoints.
   - Credentials.
   - Configuration values.
   - UI elements.
   - Test plan templates.
   - Acceptance criteria.
   - Business rules.
   - API responses.
   - Application functionality.

6. Never assume default or typical system behavior.
7. Do not silently ignore missing configuration.
8. Do not replace existing design decisions with assumptions.
9. Do not modify the original configuration or design files.
10. Keep all new implementation files inside `implementation/`.
11. Do not expose authentication credentials.
12. Do not claim an integration is working unless it has been verified.
13. Do not generate sample Jira data and present it as actual Jira data.
14. Clearly distinguish actual implementation, unverified integration, and missing functionality.

---

## EXECUTION ORDER

Execute the project in the following order:

1. Inspect the complete project structure.
2. Read `config.md`.
3. Inspect all files in `00_design/`.
4. Locate and understand the existing test plan template.
5. Identify available Jira and AI integration details.
6. Prepare the implementation architecture.
7. Create `implementation/src/frontend/`.
8. Create `implementation/src/backend/`.
9. Implement the React frontend.
10. Implement the Jira backend integration.
11. Implement requirement analysis and test plan generation.
12. Implement the Settings page.
13. Connect frontend and backend.
14. Validate configuration and security.
15. Run compilation and build checks.
16. Fix all identified implementation errors.
17. Generate the final README.

**FINAL ACCEPTANCE CRITERIA**

The task is complete only when:

- The application follows the supplied design.
- The Jira ticket input workflow is functional.
- Jira integration uses the supplied configuration.
- Jira ticket information is retrieved from the actual Jira instance when accessible.
- Test plan generation uses the supplied template.
- Generated scenarios maintain requirement traceability.
- The Settings page is implemented.
- Frontend and backend communicate successfully.
- Sensitive credentials are protected.
- The application can be started using the documented commands.

Begin by inspecting the existing files. Do not start coding before understanding the configuration, design, and test plan template.