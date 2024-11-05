<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ログインページ</title>
<!--<link rel="stylesheet" type="text/css" href="styles.css">-->
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ログインページ</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f2f4f7; /* 淡い背景色 */
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

    .container h1 {
        color: #283b5b; /* メインカラー */
        font-size: 1.5em;
        margin-bottom: 20px;
    }

    .button-container {
        margin: 15px 0;
    }

    .button {
        display: block;
        background-color: #283b5b; /* メインカラーをボタンの背景色に */
        color: #ffffff;
        padding: 15px;
        text-decoration: none;
        font-size: 1.1em;
        border-radius: 5px;
        transition: background-color 0.3s, transform 0.3s;
    }

    .button:hover {
        background-color: #4f738e; /* 少し明るい青でホバーエフェクト */
        transform: translateY(-3px); /* ホバー時に少し浮き上がる */
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
		<div class="button-container">
			<a href="LoginServlet?userType=parents" class="button">欠席・遅刻・早退
				連絡はこちら<br>（保護者の方）</a>
		</div>

		<div class="button-container">
			<a href="LoginServlet?userType=employees" class="button">教員専用画面はこちら</a>
		</div>
		
		        <div class="footer">
            © OKOCHI 2024
        </div>
	</div>

</body>
</html>

