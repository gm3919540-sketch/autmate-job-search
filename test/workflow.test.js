const test = require('node:test');
const assert = require('node:assert/strict');
const { initialState, runWorkflow, approveWorkflow, updateStatus } = require('../backend/adapter/workflow');
test('workflow generates only grounded material and waits for approval', () => { const state = runWorkflow(initialState()); assert.equal(state.workflow.state, 'WAITING_FOR_APPROVAL'); assert.equal(state.workflow.evidence.length, 5); assert.match(state.workflow.resume, /Built REST APIs with Java and Spring Boot/); });
test('submission status cannot change before approval', () => { const state = updateStatus(runWorkflow(initialState()), 'SUBMITTED'); assert.equal(state.application.status, 'DRAFT'); });
test('approved package can be tracked', () => { const state = updateStatus(approveWorkflow(runWorkflow(initialState())), 'SUBMITTED'); assert.equal(state.application.status, 'SUBMITTED'); });

