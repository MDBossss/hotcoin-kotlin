import { Request, Response } from "express";

class LoginController {
  async login(req: Request, res: Response) {
    console.log("[INFO] login()");

    const { username, password } = req.body;

    if (username === "test" && password === "test") {
      res.status(200).json({ message: "Login successful" });
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
