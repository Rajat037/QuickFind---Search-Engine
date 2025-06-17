<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>QuickFind - Search Engine</title>
    <style>
        body, html {
            margin: 0;
            padding: 0;
            height: 100%;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea, #764ba2);
            color: #333;
        }

        .container {
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            padding: 20px;
            box-sizing: border-box;
            text-align: center;
        }

        header {
            margin-bottom: 40px;
            color: #fff;
            text-shadow: 1px 1px 3px rgba(0,0,0,0.3);
        }

        header h1 {
            font-size: 3em;
            margin: 0;
            letter-spacing: 2px;
        }

        header p {
            font-size: 1.2em;
            margin-top: 8px;
            font-weight: 300;
        }

        form {
            width: 100%;
            max-width: 500px;
            display: flex;
            box-shadow: 0 4px 15px rgba(0,0,0,0.2);
            border-radius: 50px;
            overflow: hidden;
            background: #fff;
            margin-bottom: 20px;
        }

        input[type="text"] {
            flex: 1;
            padding: 15px 25px;
            border: none;
            font-size: 1.1em;
            outline: none;
            border-radius: 50px 0 0 50px;
            transition: box-shadow 0.3s ease;
        }

        input[type="text"]:focus {
            box-shadow: 0 0 8px 2px #667eea;
        }

        button {
            background-color: #764ba2;
            border: none;
            color: white;
            padding: 0 30px;
            font-size: 1.2em;
            cursor: pointer;
            border-radius: 0 50px 50px 0;
            transition: background-color 0.3s ease;
            font-weight: 600;
        }

        button:hover {
            background-color: #5a3580;
        }

        .suggestions, .history {
            background: #fff;
            box-shadow: 0 4px 10px rgba(0,0,0,0.15);
            max-width: 500px;
            width: 90%;
            text-align: left;
            margin-top: 10px;
            border-radius: 8px;
            padding: 10px 20px;
        }

        .history {
            margin-top: 30px;
        }

        .suggestions div, .history div {
            padding: 5px 0;
            cursor: pointer;
            border-bottom: 1px solid #eee;
        }

        .suggestions div:last-child,
        .history div:last-child {
            border-bottom: none;
        }

        .suggestions div:hover,
        .history div:hover {
            background-color: #f0f0f0;
        }

        .section {
            margin-top: 40px;
            background: #fff;
            padding: 25px 35px;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.15);
            max-width: 700px;
            width: 100%;
            text-align: left;
        }

        .section h3 {
            margin-bottom: 18px;
            color: #764ba2;
        }

        .section ul {
            list-style: none;
            padding-left: 0;
        }

        .section ul li {
            margin-bottom: 12px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            gap: 10px;
        }

        .section ul li a {
            color: #2980b9;
            font-weight: 500;
            text-decoration: none;
        }

        .section ul li a:hover {
            text-decoration: underline;
        }

        .copy-btn {
            padding: 5px 10px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 0.85em;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .copy-btn:hover {
            background-color: #388E3C;
        }

        footer {
            margin-top: 40px;
            color: #eee;
            font-size: 0.9em;
            font-weight: 300;
        }

        @media (max-width: 600px) {
            header h1 {
                font-size: 2em;
            }
            form {
                max-width: 90%;
            }
        }
    </style>
</head>
<body>
<div class="container">
    <header>
        <h1>QuickFind</h1>
        <p>Your fast and simple search engine</p>
    </header>

    <form action="search" method="get" autocomplete="off" onsubmit="saveSearch()">
        <input type="text" id="searchInput" name="query"
               value="<%= request.getParameter("query") != null ? request.getParameter("query") : "" %>"
               placeholder="Type your search term here..." required autofocus
               oninput="showSuggestions()" />
        <button type="submit">Search</button>
    </form>

    <!-- Suggestions and history -->
    <div class="suggestions" id="suggestionsBox" style="display: none;"></div>

    <div class="history" id="historyBox">
        <strong style="display:block; margin-bottom:5px;">Recent Searches</strong>
    </div>

    <!-- Tutorials Section (moved below suggestions/history) -->
    <div class="section">
        <h3>Tutorials </h3>
        <ul>
            <li>
                <a href="https://www.w3schools.com/html/" target="_blank">HTML Tutorial</a>
                <button class="copy-btn" onclick="copyToClipboard('https://www.w3schools.com/html/')">Copy Link</button>
            </li>
            <li>
                <a href="https://www.w3schools.com/css/" target="_blank">CSS Tutorial</a>
                <button class="copy-btn" onclick="copyToClipboard('https://www.w3schools.com/css/')">Copy Link</button>
            </li>
            <li>
                <a href="https://www.w3schools.com/js/" target="_blank">JavaScript Tutorial</a>
                <button class="copy-btn" onclick="copyToClipboard('https://www.w3schools.com/js/')">Copy Link</button>
            </li>
        </ul>
    </div>

    <footer>
        &copy; 2025 QuickFind. All rights reserved.
    </footer>
</div>

<script>
    const suggestions = [
        "Java Servlet tutorial", "What is JDBC", "Machine Learning basics",
        "GitHub portfolio ideas", "React vs Angular", "QuickSort in Java",
        "Latest tech trends", "Data Structures", "Cyber Security", "DSA interview questions"
    ];

    const input = document.getElementById("searchInput");
    const suggestionsBox = document.getElementById("suggestionsBox");
    const historyBox = document.getElementById("historyBox");

    function copyToClipboard(url) {
        navigator.clipboard.writeText(url).then(() => {
            alert("Link copied to clipboard!");
        }).catch(err => {
            console.error("Failed to copy: ", err);
        });
    }

    function showSuggestions() {
        const query = input.value.toLowerCase();
        suggestionsBox.innerHTML = "";
        if (query.length === 0) {
            suggestionsBox.style.display = "none";
            return;
        }

        const filtered = suggestions.filter(s => s.toLowerCase().includes(query));
        if (filtered.length === 0) {
            suggestionsBox.style.display = "none";
            return;
        }

        filtered.forEach(s => {
            const div = document.createElement("div");
            div.textContent = s;
            div.onclick = () => {
                input.value = s;
                suggestionsBox.style.display = "none";
            };
            suggestionsBox.appendChild(div);
        });

        suggestionsBox.style.display = "block";
    }

    function saveSearch() {
        let history = JSON.parse(localStorage.getItem("searchHistory")) || [];
        const current = input.value.trim();
        if (current && !history.includes(current)) {
            history.unshift(current);
            if (history.length > 5) history.pop();
            localStorage.setItem("searchHistory", JSON.stringify(history));
        }
    }

    function loadHistory() {
        let history = JSON.parse(localStorage.getItem("searchHistory")) || [];
        if (history.length === 0) {
            historyBox.style.display = "none";
            return;
        }

        historyBox.style.display = "block";
        history.forEach(item => {
            const div = document.createElement("div");
            div.textContent = item;
            div.onclick = () => {
                input.value = item;
                input.focus();
            };
            historyBox.appendChild(div);
        });
    }

    window.onload = loadHistory;
</script>
</body>
</html>
