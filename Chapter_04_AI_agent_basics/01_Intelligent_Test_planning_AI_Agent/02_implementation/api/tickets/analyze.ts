import type { Request, Response } from "express";
import app from "../../src/backend/src/server.js";

export default function handler(req: Request, res: Response) {
  req.url = "/api/tickets/analyze";
  return app(req, res);
}
