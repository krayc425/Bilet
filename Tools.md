# 数据库

## 改编码

1. Schema

    ```
    ALTER SCHEMA [Schema] DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci ;
    ```

2. Table

    ```
    ALTER TABLE [Table] CONVERT TO CHARACTER SET utf8mb4;
    ```


# JavaScript

## 限制输入数字及大小

```
<script type="text/javascript">
    var text = document.getElementById("number");
    text.onkeyup = function () {
        this.value = this.value.replace(/\D/g, '');
        if (this.value > 100) {
            this.value = 100;
        }
    }
</script>
```

