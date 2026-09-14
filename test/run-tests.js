const assert = require('node:assert/strict');
const { initialState, runWorkflow, approveWorkflow, updateStatus } = require('../backend/adapter/workflow');

let passed = 0;
function check(name, fn) {
  try { fn(); passed += 1; console.log(`PASS ${name}`); }
  catch (error) { console.error(`FAIL ${name}\n${error.stack}`); process.exitCode = 1; }
}

check('generated package uses retrieved verified evidence', () => {
  const state = runWorkflow(initialState());
  assert.equal(state.workflow.state, 'WAITING_FOR_APPROVAL');
  assert.equal(state.workflow.evidence.length, 5);
  assert.match(state.workflow.resume, /Built REST APIs with Java and Spring Boot/);
});
check('submission cannot occur before human approval', () => {
  const state = updateStatus(runWorkflow(initialState()), 'SUBMITTED');
  assert.equal(state.application.status, 'DRAFT');
});
check('approved packages can be tracked as submitted', () => {
  const state = updateStatus(approveWorkflow(runWorkflow(initialState())), 'SUBMITTED');
  assert.equal(state.application.status, 'SUBMITTED');
});
console.log(`${passed}/3 workflow checks passed`);

