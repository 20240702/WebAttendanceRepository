<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>教師専用ログインページ</title>
<!--    <link rel="stylesheet" type="text/css" href="loginStyles.css">-->
<style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f4f7;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .container {
            text-align: center;
            background-color: #ffffff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
            max-width: 400px;
            width: 90%;
        }

        h1 {
            color: #283b5b;
            font-size: 1.5em;
            margin-bottom: 20px;
        }

        p {
            color: #ff0000; /* エラーメッセージ用の赤色 */
            font-size: 0.9em;
        }

        .form-group {
            margin: 15px 0;
            text-align: left;
        }

        .label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #283b5b;
        }

        .input {
            width: 100%;
            padding: 10px;
            border: 1px solid #d3edf9;
            border-radius: 5px;
            box-sizing: border-box;
            font-size: 1em;
        }

        .button {
            display: inline-block;
            background-color: #283b5b;
            color: #ffffff;
            padding: 12px 20px;
            text-decoration: none;
            font-size: 1.1em;
            border-radius: 5px;
            margin-top: 10px;
            transition: background-color 0.3s, transform 0.3s;
        }

        .button:hover {
            background-color: #4f738e;
            transform: translateY(-3px);
        }

        .button-container {
            margin-top: 15px;
        }

        input[type="submit"] {
            width: 100%;
            background-color: #283b5b;
            color: #ffffff;
            padding: 12px;
            font-size: 1em;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s, transform 0.3s;
        }

        input[type="submit"]:hover {
            background-color: #4f738e;
            transform: translateY(-3px);
        }
        .footer {
        margin-top: 20px;
        font-size: 0.9em;
        color: #4f738e; /* フッターの補助的な色 */
        }
    </style>
</head>
<body>

    <div class="container">
        <h1>教師専用ログインページ</h1>
        <p><c:out value="${login.msg}" /></p>
        <form action="LoginServlet?userType=employees" method="post">
            <div class="form-group">
                <label class="label">ID</label>
                <input type="text" class="input" name="userId" placeholder="IDを入力（4桁の数字）" pattern="[0-9]{4}" required>
            </div>

            <div class="form-group">
                <label class="label">パスワード</label>
                <input type="password" class="input" name="pass" placeholder="パスワードを入力（英数字15文字）" pattern="[a-zA-Z0-9]{15}" required>
            </div>

            <input type="submit" class="button" value="ログイン">
        </form>

        <div class="button-container">
            <a href="teachersregi.jsp" class="button">新規登録はこちら</a>
        </div>

        <div class="button-container">
            <a href="index.jsp" class="button">戻る</a>
        </div>
        <div class="footer">
            © OKOCHI 2024
        </div>
        </div>
    </div>

</body>
</html>