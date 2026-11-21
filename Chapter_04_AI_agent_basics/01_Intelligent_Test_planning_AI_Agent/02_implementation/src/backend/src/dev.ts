import app from "./server.js";
import { config } from "./config.js";

app.listen(config.port, () => {
  console.log(`Backend listening on http://localhost:${config.port}`);
});
