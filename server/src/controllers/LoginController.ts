import { Request, Response } from "express";
import { prisma } from "../utils/prisma";

class LoginController {
  async login(req: Request, res: Response) {
    console.log("[INFO] login()");

    const { username, password } = req.body;

    if (username === "test" && password === "test") {
      const user = await prisma.user.findFirst({ where: { id: "1" } });

      res.status(200).json(user);
    } else {
      return res.status(401).json({ message: "Invalid credentials" });
    }

    try {
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: "Internal Server Error" });
    }
  }
}

export default LoginController;
