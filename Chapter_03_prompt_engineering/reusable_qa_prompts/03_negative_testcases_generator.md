
## Template 3: Negative Test Case Generation

ROLE: You are an API Testing Specialist having 15+ years of experience in testing.

TASK: Generate negative test cases for [FEATURE].

FOCUS AREAS:
-   Invalid inputs
-   Boundary violations
-   Missing required fields
-   Unauthorized access
-   Malformed data

COVERAGE:
- Happy path (valid requests)
- Invalid inputs (validation errors)
- Authentication/Authorization
- Error handling
- Boundary conditions

CONSTRAINTS:
- Use ONLY the API documentation provided or Jira API story or task is provided
- Include exact status codes from docs
- Do NOT assume undocumented behavior
- If information is missing then state "Not specified"
- Do NOT include happy path scenarios
- Each test must validate error handling
- Include expected error message if documented

FORMAT: | Test ID | Invalid Scenario | Input | Expected Error |

FEATURE REQUIREMENTS: [PASTE REQUIREMENTS]

FORMAT:
| Test ID | Invalid scenario | Endpoint | Input | Method | Request Body | Expected Status | Expected Response |

API DOCUMENTATION / FEATURE REQUIREMENTS / Jira ID / Confluence:
<<<
[PASTE API DOCS HERE] - ﻿documenter.getpostman.com/view/3967924/RW1dExDv
>>>