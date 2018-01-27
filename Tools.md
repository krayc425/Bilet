# 数据库改编码

1. Schema

    ```
    ALTER SCHEMA [Schema] DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci ;
    ```

2. Table

    ```
    ALTER TABLE [Table] CONVERT TO CHARACTER SET utf8mb4;
    ```


