# Test Plan — VWO-49: Add Passkey and SSO Login Options to VWO Login Page

> Status: **DRAFT — pending human review.** Not approved until a QA owner signs off.

**Organization:** Wingify / VWO  
**Product:** A/B testing and website optimization platform  
**Ticket source:** Exported Jira issue [VWO-49](https://bugzz.atlassian.net/browse/VWO-49) in `Advanced_skill_Chapter/assets/VWO-49.doc`

## 1. Scope & Objectives

- **In scope:**
  - Visibility and ordering of the “Sign in using SSO” and “Sign in with Passkey” buttons.
  - Successful and failed SSO authentication.
  - Successful passkey authentication.
  - Passkey fallback guidance and unsupported browser/device behavior.
  - Error handling, security checks, accessibility, responsiveness, and supported compatibility paths.
  - Regression checks for existing Google and email/password login options.
- **Out of scope:**
  - Passkey registration, covered by a separate story.
  - SSO provider configuration.
- **Objective:** Determine whether the new login choices are usable, securely integrated, positioned correctly, compatible with supported environments, and do not regress existing authentication methods.

## 2. Gaps & Questions for the author

| # | Area | Finding | Question to author |
|---|------|---------|--------------------|
| G1 | Functional — UI | ⚠️ Ambiguous: “below the Sign in with Google option” does not define exact spacing, order, or alignment. | What is the approved mockup and exact expected order: Google, SSO, Passkey or another order? |
| G2 | SSO | ⚠️ Ambiguous: “invalid credentials” is not paired with an exact error copy or test conditions. | Provide the exact error message and behavior for wrong password, unknown account, cancelled login, MFA failure, and provider outage. |
| G3 | SSO | ❌ Missing: configured identity providers are not listed. | Which providers must be tested and which protocols/domains are supported? |
| G4 | SSO | ❌ Missing: valid test accounts and provider-specific setup are not documented. | Who provisions authorized and unauthorized test identities, and is MFA involved? |
| G5 | Passkey | ⚠️ Ambiguous: “registered passkey is available and verified” is not a detailed verification state. | Which platform authenticator flows, registration prerequisites, and device prompts are supported? |
| G6 | Passkey | ⚠️ Ambiguous: no-passkey behavior does not specify exact fallback or guidance copy. | What fallback action should the user receive, and should unsupported and unregistered passkeys share the same message? |
| G7 | Passkey | ❌ Missing: supported browser/device matrix is absent. | Which browser, OS, and device combinations are in scope and which versions are minimum supported? |
| G8 | Passkey | ⚠️ Ambiguous: passkey cancellation and timeout states are not defined. | How should cancellation, user verification failure, timeout, and authenticator unavailable states behave? |
| G9 | Security | ⚠️ Ambiguous: “secure protocols” needs concrete implementation confirmation. | Confirm exact OAuth/SAML/WebAuthn endpoints, redirect allowlists, nonce/state handling, TLS requirements, and log masking rules. |
| G10 | Security | ❌ Missing: role, authorization, and session requirements are not stated. | What session type, timeout, logout, cookie attributes, and protected-page behavior are expected after authentication? |
| G11 | Accessibility | ❌ Missing: no keyboard, screen-reader, focus, or contrast acceptance criteria are stated. | What WCAG level and assistive technology matrix apply to the login controls and error states? |
| G12 | Responsiveness | ❌ Missing: viewport sizes and browser matrix are not stated. | Which desktop and mobile viewports/browsers are required for sign-off? |
| G13 | Internationalization | ❌ Missing: localization and text expansion behavior are not specified. | Are supported locales, pseudo-localization, and directionality in scope? |
| G14 | Performance | ❌ Missing: no timeout or response-time criteria are provided. | What is the acceptable provider redirect, authentication, and page-load threshold? |
| G15 | Observability | ❌ Missing: audit/logging requirements are not specified. | What login events must be recorded, and what sensitive fields must be redacted? |
| G16 | Environment | ❌ Missing: staging URL and feature/configuration flags are not provided in the ticket asset. | Provide the staging URL, feature flag, deployment version, and provider configuration status. |
| G17 | Test data | ❌ Missing: valid/invalid account data and passkey devices are not specified. | Where are managed test identities and registered authenticators provisioned? |
| G18 | Regression | ⚠️ Ambiguous: impact on existing login flows is implicit. | Confirm whether existing Google, email/password, Forgot Password, and Remember Me flows require full regression. |
| G19 | Rollback | ❌ Missing: rollback and disabled-feature behavior are not documented. | If the feature is disabled or provider login fails, should existing login remain available and what is the rollback state? |
| G20 | Prior execution | ⚠️ The exported comments show earlier Playwright runs with blocked test setup, not confirmed product defects. | Confirm whether the automation environment, credentials, and browser/device setup are now available for retesting. |

## 3. Test Scenarios

| ID | Priority | Type | Scenario | Maps to |
|----|----------|------|----------|---------|
| TS-01 | P0 | Positive/UI | On the login page, verify both buttons are visible, named correctly, and positioned below Google. | AC-1 |
| TS-02 | P0 | Negative/UI | Verify login-page rendering on configured desktop/mobile breakpoints and browser matrix. | AC-5; G12 |
| TS-03 | P0 | Accessibility | Navigate controls by keyboard and verify focus order, visible focus, labels, error announcements, and contrast. | AC-1; G11 |
| TS-04 | P0 | Positive/SSO | Click SSO, complete an approved provider flow with a valid identity, and verify dashboard redirect and session. | AC-2 |
| TS-05 | P0 | Negative/SSO | Cancel or deny the SSO authorization flow and verify no session or protected data is exposed. | AC-2; G2 |
| TS-06 | P0 | Negative/SSO | Attempt SSO with invalid credentials or an unauthorized identity and verify the defined safe error. | AC-2; AC-4 |
| TS-07 | P0 | Negative/SSO | Exercise configured provider outage or timeout and verify recoverable error handling. | AC-4; G14 |
| TS-08 | P0 | Security/SSO | Verify provider callbacks use the expected protocol, state/nonce or equivalent protections, and secure redirect handling. | AC-6 |
| TS-09 | P0 | Positive/Passkey | Use a registered passkey on a supported browser/device and verify successful authentication and dashboard redirect. | AC-3 |
| TS-10 | P0 | Negative/Passkey | Initiate passkey login with no registered passkey and verify fallback guidance. | AC-3 |
| TS-11 | P0 | Negative/Passkey | Cancel WebAuthn verification or fail user verification and verify the defined error state. | AC-4; G8 |
| TS-12 | P0 | Negative/Passkey | Use an unsupported browser or device and verify clear guidance with no misleading passkey success state. | AC-4; AC-5 |
| TS-13 | P1 | Compatibility/Passkey | Execute passkey login across the approved browser/OS/device matrix. | AC-5; G7 |
| TS-14 | P1 | Compatibility/SSO | Execute successful and failed login through each configured identity provider. | AC-5 |
| TS-15 | P0 | Security/Session | Verify back navigation, logout, session termination, timeout, and direct protected-page access after login and logout. | AC-6; G10 |
| TS-16 | P0 | Security/Data | Verify credentials, tokens, passkey assertions, and provider responses are not exposed in UI or normal network/application logs. | AC-6 |
| TS-17 | P1 | Resilience | Verify existing login options remain usable if SSO or passkey is unavailable or the feature flag is disabled. | Out of scope; G19 |
| TS-18 | P1 | Regression | Verify existing Google, email/password, Forgot Password, and dashboard flows remain unchanged. | G18 |
| TS-19 | P1 | Responsiveness | Verify controls and errors do not overflow or overlap on mobile and desktop viewports. | Ticket Test Scenario 7 |
| TS-20 | P1 | Observability | Verify approved success/failure audit events are recorded without sensitive values. | G15 |
| TS-21 | P2 | Internationalization | If localization is approved, verify supported locale text and layout behavior. | G13 |
| TS-22 | P2 | Performance | Measure login-page, provider redirect, and authentication response behavior against confirmed thresholds. | G14 |

## 4. Test Data & Environment

- **Data:**
  - Synthetic or approved test identities for valid and invalid SSO authentication.
  - An authorized organization account and an unauthorized account, if identity-provider policy requires them.
  - A passkey-enabled test device with an enrolled passkey and a passkey-free test profile.
  - Accounts for existing Google and email/password regression, with no production or real personal data.
- **Environment / flags:**
  - Staging deployment URL and feature/configuration flag: to be confirmed.
  - OAuth/SAML provider test tenants, redirect URIs, and protocol details: to be confirmed.
  - Approved browser, OS, device, viewport, and passkey authenticator matrix: to be confirmed.
- **Roles / permissions:**
  - Authorized and unauthorized SSO identities.
  - Existing dashboard user account.
  - Logged-out and logged-in states.

## 5. Risks & Assumptions

- **Assumptions made:**
  - VWO-49 is the intended ticket; KAN-5 was not retrievable and was clarified to VWO-49 from the asset.
  - SSO and passkey are additional login options; passkey registration and provider configuration remain separate.
  - Staging is the validation environment. Existing login options must remain available.
- **Risks:**
  - SSO may vary significantly between identity providers and MFA configurations.
  - Passkey behavior is highly dependent on browser, OS, hardware authenticator, and platform policy.
  - Exact UI, copy, session, redirect, and rate-limit behavior is not present in the ticket.
  - Earlier exported Playwright comments indicate blocked runs, but do not establish that the product is implemented or retestable.

## 6. Entry / Exit criteria

- **Entry:**
  - Ticket is approved as VWO-49 and linked to the intended build.
  - Feature is deployed to staging with SSO/passkey configuration.
  - Test accounts, providers, devices, and supported matrix are available.
  - Open blocking questions in G1–G20 have owners or accepted defaults.
- **Exit:**
  - P0 scenarios pass, or accepted P0 risk is documented.
  - P1 scenarios pass unless an agreed waiver exists.
  - No open critical/high defects.
  - Definition of Done items are complete, including QA sign-off and documentation.
  - Accessibility, security, compatibility, and regression evidence is attached.

## 7. Resource Allocation

- QA: functional, negative, boundary, accessibility, compatibility, and regression execution.
- Developer: environment/provider setup, diagnostics, defect resolution, and session/security review.
- Product/UX: exact placement, copy, mockup, fallback, and behavior sign-off.
- Security/Identity: protocol, session, callback, logging, and provider review.
- Automation: after blocking setup gaps are resolved, prioritization of UI, SSO positive/negative, passkey capability checks, and regression.

## 8. Timelines, Sprint Details

- Sprint: **Not specified in the exported ticket.**
- Fix version: **Not specified.**
- Remaining estimate: **Not specified.**
- Timeline should be added after environment readiness and test-data provisioning are confirmed.

---

## HUMAN REVIEW GATE

- **I assumed:** VWO-49 is the asset-backed ticket to plan. The standard P0/P1/P2 priorities reflect authentication risk; exact product behavior not stated in the ticket is not invented.
- **I could not confirm:** The staging URL, provider list, passkey support matrix, exact copy/mockup, session policy, locale/performance targets, test accounts, and automation environment.
- **Open questions blocking sign-off:** G1–G20, especially G3, G4, G7, G10, G11, G12, G16, and G17.
- **Approve or edit before I write test cases or automation.**
