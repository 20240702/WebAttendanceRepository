<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>出欠情報削除確認</title>
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

        .message {
            margin: 20px 0;
            font-size: 1.1em;
        }

        .button-container {
            margin: 15px 0;
        }

        .submit-button {
            display: inline-block;
            background-color: #f44336; /* 決定ボタンの背景色を赤に変更 */
            color: #ffffff;
            padding: 15px; /* パディングを元に戻す */
            text-decoration: none;
            font-size: 1.1em;
            border-radius: 5px;
            transition: background-color 0.3s, transform 0.3s;
        }

        .submit-button:hover {
            background-color: #c62828; /* 決定ボタンのホバー色 */
            transform: translateY(-3px); /* ホバー時に少し浮き上がる */
        }

        .cancel-button {
            display: inline-block;
            background-color: #283b5b; /* キャンセルボタンの背景色を青に変更 */
            color: #ffffff; /* キャンセルボタンの文字色 */
            padding: 12px 20px; /* パディングを追加 */
            border-radius: 5px; /* 角を丸める */
            text-decoration: none; /* 下線を消す */
            transition: background-color 0.3s, transform 0.3s; /* ホバー時の効果 */
        }

        .cancel-button:hover {
            background-color: #4f738e; /* キャンセルボタンのホバー色 */
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
    <h1>削除確認</h1>
        <p class="message">${delTblAttendance.atteRecord}の${delTblAttendance.studName}さんの出欠情報を削除してもよろしいですか？</p>
        <form action="ParentsMainServlet" method="post">
            <input type="hidden" name="process" value="executeDeleteAtte">
            <div class="button-container">
                <input type="submit" value="はい" class="submit-button"> <!-- 決定ボタン -->
            </div>
        </form>
        <div class="button-container">
            <a href="ParentsMainServlet" class="cancel-button">キャンセル</a> <!-- キャンセルボタン -->
        </div>
        <div class="footer">
            © OKOCHI 2024
        </div>
    </div>
</body>
</html>
