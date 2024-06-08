import { Request, Response } from "express";
import { prisma } from "../utils/prisma";

class LoginController {
  async register(req: Request, res: Response) {
    try {
      const { username, password } = req.body;

      const existingUser = await prisma.user.findFirst({
        where: { username },
      });

      if (existingUser) {
        return res
          .status(400)
          .json({ message: "User with this username already exists" });
      }

      const newUser = await prisma.user.create({
        data: {
          username,
          password,
          imageUrl:
            "https://images.unsplash.com/photo-1510771463146-e89e6e86560e?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8ZG9nJTIwcHJvZmlsZSUyMHBpY3R1cmV8ZW58MHx8MHx8fDA%3D",
        },
      });

      return res.status(201).json(newUser);
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: "Internal Server Error" });
    }
  }

  async login(req: Request, res: Response) {
    try {
      console.log("[INFO] login()");

      const { username, password } = req.body;

      const user = await prisma.user.findFirst({
        where: { username },
      });

      if (!user) {
        return res.status(401).json({ message: "Invalid credentials" });
      }

      const passwordMatch = password === user.password;
      if (!passwordMatch) {
        return res.status(401).json({ message: "Invalid credentials" });
      }

      console.log(`[INFO] User ${username} logged in at ${new Date()}`);

      res.status(200).json(user);
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: "Internal Server Error" });
    }
  }
}

export default LoginController;
