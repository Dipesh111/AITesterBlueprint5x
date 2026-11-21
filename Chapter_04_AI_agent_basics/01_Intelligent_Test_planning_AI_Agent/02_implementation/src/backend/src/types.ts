export interface JiraTicket {
  key: string;
  summary: string;
  description: string;
  issueType: string;
  priority: string;
  status: string;
  assignee: string;
  reporter: string;
  acceptanceCriteria: string;
  labels: string[];
  components: string[];
  comments: string[];
  attachments: string[];
}

export interface TestScenario {
  id: string;
  priority: string;
  type: string;
  scenario: string;
  mapsTo: string;
}

export interface TestPlan {
  title: string;
  status: string;
  scope: string[];
  gaps: string[];
  scenarios: TestScenario[];
  data: string[];
  risks: string[];
  sourceText: string;
}
