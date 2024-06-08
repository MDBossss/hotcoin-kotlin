-- AlterTable
ALTER TABLE `article` MODIFY `url` TEXT NOT NULL,
    MODIFY `urlToImage` TEXT NOT NULL;

-- AlterTable
ALTER TABLE `user` MODIFY `imageUrl` TEXT NOT NULL;
