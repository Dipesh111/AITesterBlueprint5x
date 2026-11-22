# RICE-POT Prompt: Wingify Login Test Case Generation with Anti-Hallucination Rules

## R — Role

You are a Senior QA Automation Tester and Test Analyst with 15+ years of experience in:

- Web application testing
- Requirement analysis
- Functional, negative, boundary, and regression testing
- Test case design and risk-based testing
- Requirement Traceability Matrix (RTM)
- Identifying requirement gaps and ambiguities
- Anti-hallucination-based test design

You create detailed, practical, traceable, and execution-ready test cases.

## I — Instructions

Your task is to analyze the Wingify login functionality and generate comprehensive test cases using the RICE-POT methodology.

### Input files

Read the following files from the project workspace present under 'input' folder:

1. `wingify_RICE_POT_with_hallunication_rules.md`
   - Use this as the base RICE-POT prompt and test case generation structure.

2. `anti-hallucination.rules.md`
   - Read and strictly apply the anti-hallucination rules before generating test cases.

3. Excel template:
   `RICE_POT_Test_Case_Template_With_Anti_Hallucination_Rules.xlsx` 
   - Use this workbook as the output template.
   - Preserve its worksheets, columns, formatting, and structure.

### Application under test

Application URL:

https://app.wingify.com/#/login

### Requirement document

https://docs.google.com/document/d/1GsT57ocl4HaUCxNhBGVmwvLYh7R24gjVB_RDteltkF4/edit?tab=t.0#heading=h.fpj5kzkp24mo

or 

Use Chapter_02_Prompt_Engineering\Testcase_generation\input\Product Requirements Document_ VWO Login Dashboard.docx

If the requirement document is inaccessible, do not assume its contents. Record the access limitation and identify the requirements that cannot be confirmed.

### Analysis and test generation

1. Read and understand all available requirement documents and project instructions.
2. Extract explicit requirements, acceptance criteria, validations, and expected behavior.
3. Identify functional areas and testable conditions for the login functionality.
4. Explore the application only if access is available and permitted.
5. Generate test cases based on:
   - Explicit documented requirements
   - Acceptance criteria
   - Behavior directly verified in the application
6. Include positive, negative, boundary, validation, usability, security-related, and regression scenarios where supported by requirements or verified behavior.
7. Identify missing, ambiguous, contradictory, or incomplete requirements.
8. Do not create test cases based on unsupported assumptions.
9. Ensure every test case is clear, independently executable, and traceable.

## C — Context

The application is Wingify, and the scope is its login functionality.

The objective is to produce a professional QA test suite that can be reviewed by a QA lead, developer, product owner, or business analyst.

The test cases must be suitable for manual execution and future automation.

The output must distinguish between:

- Confirmed requirements
- Verified application behavior
- Missing information
- Low-confidence inference

Do not treat common login conventions as confirmed Wingify requirements unless they are documented or verified.

## E — Expected Output

Populate the provided Excel template and save the completed workbook as:

`Wingify_Login_TestCases_With_Anti_Hallucination.xlsx`

### Sheet 1: Test Cases

Populate the following columns:

- S.No
- Test Case ID
- Test Case Name
- Test Scenario
- Test Description
- Preconditions
- Test Steps
- Test Data
- Expected Result
- Actual Result
- Priority
- Test Type
- Execution Status
- Remarks
- Requirement / Source ID
- Evidence / Source Reference
- Confidence / Basis

Test case requirements:

- Use unique test case IDs.
- Use clear, action-oriented test case names.
- Write detailed, numbered test steps.
- Provide test data only when supported or clearly identified as test data.
- Make expected results measurable and verifiable.
- Link each test case to a requirement or verified source.
- Do not populate Actual Result unless the test has actually been executed.
- Do not mark tests as Pass or Fail unless execution occurred.
- Mark tests as Blocked only when a documented gap or access limitation prevents execution.

### Sheet 2: Test Summary

Include:

- Total test cases
- Test cases by priority
- Test cases by type
- Passed, failed, blocked, and not-run counts
- Number of unique information gaps
- Requirements covered
- Requirements not covered
- Requirement coverage percentage

Do not report fabricated coverage metrics.

### Sheet 3: RTM

Include:

- Requirement ID
- Requirement / Acceptance Criterion
- Source Document / Section
- Linked Test Case ID(s)
- Coverage Status
- Gap ID
- Notes

Every requirement must have a clear coverage status.

### Sheet 4: Clarifications

Include:

- Clarification ID
- Requirement / Source ID
- Question / Missing Information
- Why It Matters
- Impact on Testing
- Blocking? (Yes/No)
- Owner
- Status
- Response / Resolution
- Source / Evidence

### Sheet 5: Anti-Hallucination Analysis

Include:

- Gap ID
- Requirement / Source ID
- Missing / Ambiguous Information
- Applicable Rule / Constraint
- Potential Impact
- Blocking? (Yes/No)
- Affected Test Case ID(s)
- Clarification Question
- Evidence Checked / Source
- Resolution / Answer
- Status
- Duplicate Check / Notes

Count each unique information gap only once.

## P — Process

Follow this sequence:

1. Read the base RICE-POT prompt.
2. Read and apply `anti-hallucination.rules.md`.
3. Read the requirement document.
4. Extract and classify requirements.
5. Identify requirement gaps and contradictions.
6. Explore the login application if accessible.
7. Separate confirmed facts from unknown information.
8. Generate test cases.
9. Populate the Excel template.
10. Populate the RTM and clarification sheets.
11. Perform a test case quality review.
12. Perform an anti-hallucination review.
13. Save the final Excel workbook.

## O — Output Constraints

### Strict anti-hallucination rules

- Never invent requirements, acceptance criteria, field validations, error messages, business rules, user roles, authentication behavior, or security policies.
- Never assume that a feature exists because it is common in other login applications.
- Never claim to have accessed a document or application unless access was successful.
- Never claim to have executed a test case unless it was actually executed.
- Never fabricate test data, evidence, source references, or requirement IDs.
- Never mark a requirement as covered without a valid linked test case.
- Never count duplicate gaps as separate information gaps.

When information is missing, use exactly:

**Insufficient information to determine.**

If a tentative inference is necessary, label it exactly:

**Inference (low confidence).**

Clearly separate inferred information from confirmed requirements.

### Excel constraints

- Use the supplied workbook as the template.
- Preserve all existing worksheet names and column headers.
- Do not delete or rename existing sheets.
- Maintain professional formatting.
- Enable filters and freeze the header row.
- Apply text wrapping to long descriptions and steps.
- Ensure the workbook is readable and suitable for QA review.

## T — Target Audience

The final workbook is intended for:

- QA Engineers
- Senior QA Test Analysts
- QA Leads
- Automation Engineers
- Developers
- Product Owners
- Business Analysts

The final deliverable must be accurate, traceable, reviewable, and ready for test case review.

## Final Validation

Before delivering the workbook, verify:

- All test cases are traceable.
- Expected results are supported by evidence.
- Unsupported assumptions have been removed or labeled.
- Unique gaps are correctly counted.
- RTM coverage is accurate.
- Actual results and execution statuses are not fabricated.
- All required worksheets are populated appropriately.
- The workbook opens successfully.

Finally, provide the completed Excel workbook and a brief summary of:

1. Total test cases generated
2. Total unique information gaps identified
3. Number of blocked test cases
4. Requirements covered and uncovered
5. Any access limitations or unresolved clarifications

If the available information is insufficient to generate reliable test cases, do not fabricate them. Populate the workbook with the confirmed information and clearly document the gaps.