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

# CSS

## 表格居中

水平居中

```
<th style='text-align: center;'>host</th>
```

垂直居中

```
<td rowspan=$row_host1 style='vertical-align: middle;'>host1</td>
```

既水平又垂直居中

```
<td rowspan=$rowspan style='vertical-align: middle;text-align: center;'>hello</td>
```

