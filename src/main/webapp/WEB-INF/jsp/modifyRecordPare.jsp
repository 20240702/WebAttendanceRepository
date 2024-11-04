<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>出欠情報修正</title>
 <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f7f7f7;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .container {
            background-color: #ffffff;
            padding: 20px 30px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            max-width: 400px;
            width: 90%;
            text-align: left;
        }

        h1 {
            color: #283b5b;
            font-size: 1.5em;
            margin-bottom: 15px;
        }

        label {
            display: block;
            color: #283b5b;
            margin-top: 10px;
            font-weight: bold;
        }

        input[type="time"],
        input[type="text"],
        select {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            margin-bottom: 15px;
            border: 1px solid #d3edf9;
            border-radius: 5px;
            box-sizing: border-box;
            font-size: 1em;
        }

        input[type="submit"] {
            background-color: #f44336; /* 決定ボタンの背景色を赤に変更 */
            color: #ffffff;
            padding: 12px 20px;
            border: none;
            border-radius: 5px;
            font-size: 1em;
            cursor: pointer;
            width: 100%;
            transition: background-color 0.3s, transform 0.3s;
        }

        input[type="submit"]:hover {
            background-color: #c62828; /* 決定ボタンのホバー色 */
            transform: translateY(-3px); /* ホバー時に少し浮き上がる */
        }

        a.cancel-button {
            display: block;
            text-align: center;
            margin-top: 20px;
            background-color: #283b5b; /* キャンセルボタンの背景色を青に変更 */
            color: #ffffff; /* キャンセルボタンの文字色 */
            padding: 12px 20px; /* パディングを追加 */
            border-radius: 5px; /* 角を丸める */
            text-decoration: none; /* 下線を消す */
            transition: background-color 0.3s, transform 0.3s; /* ホバー時の効果 */
        }

        a.cancel-button:hover {
            background-color: #4f738e; /* キャンセルボタンのホバー色 */
            transform: translateY(-3px); /* ホバー時に少し浮き上がる */
        }

        .footer {
            margin-top: 20px;
            font-size: 0.9em;
            color: #4f738e;
        }
    </style>
<script>
function toggleFormFields() {
    var status = document.getElementById('status').value;
    var arriTime = document.getElementById('arriTime');
    var depaTime = document.getElementById('depaTime');

    arriTime.style.display = 'none';
    depaTime.style.display = 'none';

    if (status === '遅刻／早退') {
        arriTime.style.display = 'block';
        depaTime.style.display = 'block';
    } else if (status === '遅刻') {
        arriTime.style.display = 'block';
    } else if (status === '早退') {
        depaTime.style.display = 'block';
    }
}

document.addEventListener('DOMContentLoaded', function () {
    toggleFormFields();

    document.getElementById('status').addEventListener('change', function () {
        toggleFormFields();
    });
});
</script>
</head>
<body>
    <div class="container">
        <h1>出欠情報を修正する</h1>
        <form action="ParentsMainServlet" method="post">
            <label for="status">出欠等</label>
            <select name="atteStatus" id="status">
                <option value="${modTblAttendance.atteStatus}">現在の選択：${modTblAttendance.atteStatus}</option>
                <option value="欠席">欠席</option>
                <option value="遅刻／早退">遅刻／早退</option>
                <option value="遅刻">遅刻</option>
                <option value="早退">早退</option>
            </select>

            <label for="arriTime">登校時刻</label>
            <input type="time" name="atteArriTime" id="arriTime" value="${modTblAttendance.atteArriTimeHtml}">

            <label for="depaTime">下校時刻</label>
            <input type="time" name="atteDepaTime" id="depaTime" value="${modTblAttendance.atteDepaTimeHtml}">

            <label for="atteInf">出欠詳細情報</label>
            <select name="atteInf">
                <option value="${modTblAttendance.atteInf}">現在の選択：${modTblAttendance.atteInf}</option>
                <option value="なし">なし（遅刻または早退）</option>
                <option value="病欠">病欠</option>
                <option value="家事都合">家事都合</option>
                <option value="忌引き">忌引き</option>
                <option value="出席停止">出席停止</option>
            </select>

            <label for="atteRemarks">備考</label>
            <input type="text" name="atteRemarks" value="${modTblAttendance.atteRemarks}">

            <input type="hidden" name="process" value="applyModify">
            <input type="submit" value="修正">
        </form>
        <a href="ParentsMainServlet"class="cancel-button">キャンセル</a>
        <div class="footer">
            © OKOCHI 2024
        </div>
    </div>
</body>
</html>
