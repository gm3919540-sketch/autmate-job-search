const http = require('node:http');
const fs = require('node:fs');
const path = require('node:path');
const { initialState, runWorkflow, approveWorkflow, updateStatus } = require('./workflow');

const root = path.resolve(__dirname, '../..');
const dataPath = path.join(root, 'data', 'state.json');
fs.mkdirSync(path.dirname(dataPath), { recursive: true });
let state = fs.existsSync(dataPath) ? JSON.parse(fs.readFileSync(dataPath, 'utf8')) : initialState();
const save = () => fs.writeFileSync(dataPath, JSON.stringify(state, null, 2));
const json = (res, code, body) => { res.writeHead(code, { 'content-type': 'application/json; charset=utf-8' }); res.end(JSON.stringify(body)); };

const server = http.createServer((req, res) => {
  const url = new URL(req.url, 'http://localhost');
  if (req.method === 'GET' && url.pathname === '/api/state') return json(res, 200, state);
  if (req.method === 'POST' && url.pathname === '/api/workflow/run') { state = runWorkflow(state); save(); return json(res, 200, state); }
  if (req.method === 'POST' && url.pathname === '/api/workflow/approve') { state = approveWorkflow(state); save(); return json(res, 200, state); }
  if (req.method === 'POST' && url.pathname === '/api/application/status') { state = updateStatus(state, url.searchParams.get('status')); save(); return json(res, 200, state); }
  if (req.method === 'POST' && url.pathname === '/api/reset') { state = initialState(); save(); return json(res, 200, state); }
  const file = url.pathname === '/' ? 'frontend/public/index.html' : `frontend/public${url.pathname}`;
  const filePath = path.normalize(path.join(root, file));
  if (!filePath.startsWith(path.join(root, 'frontend', 'public')) || !fs.existsSync(filePath)) return json(res, 404, { error: 'Not found' });
  const type = filePath.endsWith('.css') ? 'text/css' : filePath.endsWith('.js') ? 'text/javascript' : 'text/html';
  res.writeHead(200, { 'content-type': `${type}; charset=utf-8` }); fs.createReadStream(filePath).pipe(res);
});

if (require.main === module) server.listen(process.env.PORT || 4173, () => console.log('ApplyFlow is running at http://localhost:4173'));
module.exports = server;

