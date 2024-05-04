document.addEventListener('DOMContentLoaded', function () {
    // 카테고리 데이터 로딩
    fetchCategoriesAndSetupMenu();

    // 회원가입, 로그인, 로그아웃 버튼 이벤트 리스너 설정
    setupAuthenticationEventListeners();
});

function fetchCategoriesAndSetupMenu() {
    fetch('http://localhost:9000/category/list')
    .then(response => response.json())
    .then(categories => {
        const menu = document.getElementById('category-menu');
        let initialLoad = true;
        categories.push({name: '🔥IssueTop10🔥'});
        categories.push({name: '🐵Recommend20🐵'});

        categories.push({name: 'errorHub'}); // errorHub 추가

        categories.forEach(category => {
            const menuItem = document.createElement('li');
            menuItem.textContent = category.name;
            menuItem.setAttribute('data-repo', category.name);
            menuItem.addEventListener('click', function() {
                document.querySelectorAll('.category-menu li').forEach(item => {
                    item.classList.remove('selected');
                });
                menuItem.classList.add('selected');
                handleCategorySelection(this.getAttribute('data-repo'));
            });
            menu.appendChild(menuItem);
            if (initialLoad) {
                handleCategorySelection(category.name);
                initialLoad = false;
            }
        });
    })
    .catch(error => console.error('Error loading categories:', error));
}

function handleCategorySelection(repo) {
    if (repo === 'errorHub') {
        fetchAndDisplayIssuesForErrorHub();
        document.getElementById('readmePreview').style.display = 'none';
        document.getElementById('writeButton').style.display = 'block';
    } else if (repo === '🔥IssueTop10🔥') {
        fetchAndDisplayTopSearchedIssues();
        document.getElementById('readmePreview').style.display = 'none';
        document.getElementById('writeButton').style.display = 'none';
    } else if (repo === '🐵Recommend20🐵') {
        fetchAndDisplayRecommendedIssues();
        document.getElementById('readmePreview').style.display = 'none';
        document.getElementById('writeButton').style.display = 'none';
    } else {
        fetchAndDisplayIssues(repo);
        fetchAndDisplayReadmePreview(repo);
        document.getElementById('writeButton').style.display = 'none';
    }
}

function setupAuthenticationEventListeners() {
    document.querySelector('.join-link').addEventListener('click', function () {
        const username = prompt("Enter your username:");
        const password = prompt("Enter your password:");

        if (!username.trim() || !password.trim()) {
            alert("회원가입 실패: 모든 필드를 채워주세요!");
            return;
        }

        fetch('http://localhost:9000/join', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({username, password})
        })
        .then(response => response.json())
        .then(data => alert("회원가입 성공!"))
        .catch(error => alert("회원가입 실패: " + error));
    });

    document.querySelector('.login-link').addEventListener('click', function () {
        const username = prompt("Enter your username:");
        const password = prompt("Enter your password:");

        fetch('http://localhost:9000/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({username, password})
        })
        .then(response => response.json())  // JSON으로 응답을 처리
        .then(data => {
            alert(data.message);  // 응답에서 메시지 필드 사용
            if(data.memberId) {
                sessionStorage.setItem('memberId', data.memberId);  // memberId 세션 스토리지에 저장
            }
            document.querySelector('.logout-link').style.display = 'block';
            document.querySelector('.login-link').style.display = 'none';
            document.querySelector('.join-link').style.display = 'none';
        })
        .catch(error => alert("로그인 실패: " + error.message));
    });





    document.querySelector('.logout-link').addEventListener('click', function () {
        fetch('http://localhost:9000/logout', {
            method: 'POST'
        })
        .then(response => response.json())
        .then(data => {
            alert(data.message);
            document.querySelector('.logout-link').style.display = 'none';
            document.querySelector('.login-link').style.display = 'inline-block';
            document.querySelector('.join-link').style.display = 'inline-block';
        })
        .catch(error => alert("로그아웃 실패: " + error));

    });
}


async function fetchAndDisplayIssues(categoryName) {
    const issuesContainer = document.getElementById('issues');
    issuesContainer.innerHTML = '';
    const response = await fetch(`http://localhost:9000/issues/list/repo?repo=${categoryName}`);
    const issues = await response.json();
    issues.forEach(issue => {
        const card = document.createElement('div');
        card.className = 'issue-card';
        card.innerHTML = `<h2>${issue.title}</h2>
                          <p>Repository: ${issue.repo} | Status: <span class="${issue.status}">${issue.status}</span></p>`;
        card.addEventListener('click', () => {
            showIssueDetails(issue.id);
        });
        issuesContainer.appendChild(card);
    });
}

async function showIssueDetails(issueId, shouldShowModal = true) {
    const modal = document.getElementById('issueModal');
    try {
        const response = await fetch(`http://localhost:9000/issues/detail?id=${issueId}`);
        const issueDetails = await response.json();

        document.getElementById('modalTitle').textContent = issueDetails.title || 'No Title Available';
        document.getElementById('modalRepo').textContent = 'Repository: ' + (issueDetails.repo || 'No Repository');
        document.getElementById('modalStatus').textContent = 'Status: ' + (issueDetails.status || 'No Status');
        document.getElementById('modalOwner').textContent = 'Owner: ' + (issueDetails.owner || 'No Owner');
        document.getElementById('modalUpdateDate').textContent = 'Updated: ' + (issueDetails.updateDate ? new Date(issueDetails.updateDate).toLocaleDateString() : 'No Date Information');

        // Ensure all information is visible if previously hidden
        document.getElementById('modalRepo').style.display = '';
        document.getElementById('modalStatus').style.display = '';
        document.getElementById('modalOwner').style.display = '';
        document.getElementById('modalUpdateDate').style.display = '';

        if (issueDetails.detail) {
            try {
                document.getElementById('modalDetail').innerHTML = marked.marked(issueDetails.detail);
            } catch (err) {
                console.error("Error using marked:", err);
                document.getElementById('modalDetail').textContent = 'Details formatting error.';
            }
        } else {
            document.getElementById('modalDetail').textContent = 'No details are available for this issue.';
        }

        if (shouldShowModal) {
            modal.style.display = 'block';
        }
    } catch (error) {
        console.error('Error loading issue details:', error);
        document.getElementById('modalDetail').textContent = 'Failed to load detailed information.';
    }
}



document.querySelector('.close').addEventListener('click', () => {
    document.getElementById('issueModal').style.display = 'none';
});

window.addEventListener('click', event => {
    const modal = document.getElementById('issueModal');
    if (event.target === modal) {
        modal.style.display = 'none';
    }
});

async function fetchAndDisplayReadmePreview(repo) {
    const readmePreviewContainer = document.getElementById('readmePreview');

    if (!repo) {
        console.error('Repository name is not provided');
        readmePreviewContainer.innerHTML = '<p>Repository not specified.</p>';
        readmePreviewContainer.style.display = 'block';
        return;
    }

    try {
        const response = await fetch(`http://localhost:9000/project/readme?repo=${repo}`);
        const readmeData = await response.json();

        if (readmeData && readmeData.owner && readmeData.repo) {
            readmePreviewContainer.innerHTML = `
                <div>${readmeData.owner}</div>
                <div>${readmeData.repo}</div>
            `;

            readmePreviewContainer.style.display = 'block';

            // Remove all previous event listeners
            const new_element = readmePreviewContainer.cloneNode(true);
            readmePreviewContainer.parentNode.replaceChild(new_element, readmePreviewContainer);

            // Add new event listener to the new element
            new_element.addEventListener('click', () => showReadmeModal(repo));
        } else {
            readmePreviewContainer.innerHTML = '<p>No README available.</p>';
            readmePreviewContainer.style.display = 'block';
        }
    } catch (error) {
        console.error('Error loading README preview:', error);
        readmePreviewContainer.innerHTML = '<p>Error loading README.</p>';
        readmePreviewContainer.style.display = 'block';
    }
}

async function showReadmeModal(repo) {
    try {
        const response = await fetch(`http://localhost:9000/project/readme/detail?repo=${repo}`);
        const details = await response.json();
        if (!details) {
            console.error('README details are not available');
            return;
        }

        const modal = document.getElementById('issueModal');
        // Repository Information Not Available 대신 실제 리포지토리 이름 표시
        document.getElementById('modalTitle').textContent = repo || 'Repository Information Not Available';

        // 모달 상세 정보 설정
        const modalDetail = document.getElementById('modalDetail');
        modalDetail.innerHTML = ''; // 기존 내용 초기화

        let content = details.content || '';
        content = convertAsciiDocImagesToHTML(content);

        content = marked.parse(content);

        modalDetail.innerHTML = `<div style="font-size: 0.8em; overflow-y: auto; max-height: 60vh;">${content}</div>`;

        // 정보 표시 부분을 숨김 처리
        document.getElementById('modalRepo').style.display = 'none';
        document.getElementById('modalStatus').style.display = 'none';
        document.getElementById('modalOwner').style.display = 'none';
        document.getElementById('modalUpdateDate').style.display = 'none';

        modal.style.display = 'block';
    } catch (error) {
        console.error('Failed to load README details:', error);
    }
}

function convertAsciiDocImagesToHTML(text) {
    return text.replace(/image::(.*?)\[(.*?)\]/g, function(match, p1, p2) {
        let parts = p2.split(',');
        let alt = parts[0].trim();
        let title = parts.length > 1 ? parts[1].trim().replace(/link="(.+?)"/, '') : '';
        let src = p1.trim();
        let link = p1.match(/link="(.+?)"/);

        return `<a href="${link ? link[1] : '#'}"><img src="${src}" alt="${alt}" title="${title}"></img></a>`;
    });
}

document.getElementById('search-button').addEventListener('click', function() {
    const keyword = document.getElementById('search-input').value;
    const selectedCategory = document.querySelector('.category-menu li.selected');

    if (keyword.length > 0) {
        if (selectedCategory && selectedCategory.textContent === "errorHub") {
            searchIssuesOnErrorBoard(keyword);
        } else {
            searchIssues(keyword);
        }
    } else {
        fetchAndDisplayIssues('defaultCategory'); // 기본 카테고리나 전체 리스트를 다시 표시
    }
});

async function searchIssues(keyword) {
    const issuesContainer = document.getElementById('issues');
    issuesContainer.innerHTML = ''; // Clear previous issues
    document.getElementById('readmePreview').style.display = 'none'; // Hide README preview during search

    try {
        const response = await fetch(`http://localhost:9000/issues/search?keyword=${keyword}`);
        const issues = await response.json();
        issues.forEach(issue => {
            const card = document.createElement('div');
            card.className = 'issue-card';
            card.innerHTML = `<h2>${issue.title}</h2>
                              <p>Repository: ${issue.repo} | Status: <span class="${issue.status}">${issue.status}</span></p>`;
            card.addEventListener('click', () => {
                showIssueDetails(issue.id);
            });
            issuesContainer.appendChild(card);
        });
    } catch (error) {
        console.error('Error loading issues:', error);
        issuesContainer.innerHTML = '<p>Error loading issues.</p>';
    }
}

async function searchIssuesOnErrorBoard(keyword) {
    const issuesContainer = document.getElementById('issues');
    issuesContainer.innerHTML = ''; // Clear previous issues
    document.getElementById('readmePreview').style.display = 'none'; // Hide README preview during search

    try {
        const response = await fetch(`http://localhost:9000/error_board/search?keyword=${keyword}`);
        const issues = await response.json();
        issues.forEach(issue => {
            const card = document.createElement('div');
            card.className = 'issue-card';
            card.innerHTML = `
                <h2>${issue.name}</h2>`;
            card.addEventListener('click', () => {
                showErrorBoardDetails(issue.id);
            });
            issuesContainer.appendChild(card);
        });
    } catch (error) {
        console.error('Error loading issues from error board:', error);
        issuesContainer.innerHTML = '<p>Error loading issues.</p>';
    }
}

async function showErrorBoardDetails(issueId) {
    const modal = document.getElementById('issueModal');

    // 모달 내용 초기화
    document.getElementById('modalTitle').textContent = '';
    document.getElementById('modalDetail').innerHTML = '';
    document.getElementById('modalRepo').textContent = '';
    document.getElementById('modalStatus').textContent = '';
    document.getElementById('modalOwner').textContent = '';
    document.getElementById('modalUpdateDate').textContent = '';

    try {
        const response = await fetch(`http://localhost:9000/error_board/detail?id=${issueId}`);
        const issueDetails = await response.json();

        modalTitle.textContent = issueDetails.name;
        modalDetail.innerHTML = marked.parse(issueDetails.content);
        modal.style.display = 'block';
    } catch (error) {
        console.error('Error loading issue details:', error);
        modalDetail.textContent = 'Failed to load detailed information.';
    }
}
function showWriteModal() {
    // 모달 내용 초기화
    document.getElementById('modalTitle').textContent = '';
    document.getElementById('modalDetail').innerHTML = '';
    document.getElementById('modalRepo').textContent = '';
    document.getElementById('modalStatus').textContent = '';
    document.getElementById('modalOwner').textContent = '';
    document.getElementById('modalUpdateDate').textContent = '';

    const modal = document.getElementById('issueModal');
    modal.style.display = 'block'; // 모달을 보여줌
    document.getElementById('modalTitle').textContent = '새 글 작성';
    document.getElementById('modalDetail').innerHTML = `
        <input type="text" id="name" placeholder="제목" required style="font-size: 0.8rem; margin-bottom: 5px;"><br>
        <textarea id="content" placeholder="내용" required style="font-size: 0.8rem; height: 100px;"></textarea><br>
        <input type="password" id="password" placeholder="비밀번호" required style="font-size: 0.8rem; margin-bottom: 5px;"><br>
        <button class="button" id="submitButton">작성</button>
    `;

    const submitButton = document.getElementById('submitButton');
    submitButton.addEventListener('click', submitWriteForm);
}

async function fetchAndDisplayIssuesForErrorHub() {
    const issuesContainer = document.getElementById('issues');
    issuesContainer.innerHTML = ''; // Clear previous issues
    const response = await fetch(`http://localhost:9000/error_board/all`);
    const issues = await response.json();
    issues.forEach(issue => {
        const card = document.createElement('div');
        card.className = 'issue-card';
        card.innerHTML = `<h2>${issue.name}</h2>`;
        card.addEventListener('click', () => {
            showErrorBoardDetails(issue.id);
        });
        issuesContainer.appendChild(card);
    });
}


function submitWriteForm() {
    const data = {
        name: document.getElementById('name').value,
        content: document.getElementById('content').value,
        password: document.getElementById('password').value
    };

    fetch('http://localhost:9000/error_board/write', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => response.json())
    .then(data => {
        if (data.status === "success") {
            console.log('Form submitted successfully:', data);
            closeModal();
        } else {
            console.error('Form submission failed:', data);
        }
    })
    .catch(error => {
        console.error('Error submitting form:', error);
    });
}

function closeModal() {
    const modal = document.getElementById('issueModal');
    modal.style.display = 'none'; // 모달 닫기
}

async function fetchAndDisplayTopSearchedIssues() {
    const issuesContainer = document.getElementById('issues');
    issuesContainer.innerHTML = ''; // Clear previous issues

    try {
        const response = await fetch('http://localhost:9000/issues/top-searched');
        const issues = await response.json();
        issues.forEach(issue => {
            const card = document.createElement('div');
            card.className = 'issue-card';
            card.innerHTML = `<h2>${issue.title}</h2>
                              <p>Repository: ${issue.repo} | Status: <span class="${issue.status}">${issue.status}</span></p>`;
            card.addEventListener('click', () => {
                showIssueDetails(issue.id);
            });
            issuesContainer.appendChild(card);
        });
    } catch (error) {
        console.error('Error loading top searched issues:', error);
        issuesContainer.innerHTML = '<p>Error loading top searched issues.</p>';
    }

}

async function fetchAndDisplayRecommendedIssues() {
    const memberId = sessionStorage.getItem('memberId'); // 세션 스토리지에서 memberId 가져오기
    console.log("Current memberId:", memberId); // 콘솔에 memberId 출력

    const issuesContainer = document.getElementById('issues');
    issuesContainer.innerHTML = ''; // Clear previous issues

    if (!memberId) {
        console.error('No memberId found. Please login.');
        issuesContainer.innerHTML = '<p>Please login to see recommended issues.</p>';
        return;
    }

    const url = `http://localhost:9000/issues/list/recommend?memberId=${memberId}`;
    console.log("Calling API with URL:", url); // API 호출 URL 콘솔에 출력

    try {
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error(`Network response was not ok: ${response.statusText}`);
        }
        const issues = await response.json();
        console.log("Received issues:", issues); // 받은 이슈 로그 출력

        if (issues.length === 0) {
            issuesContainer.innerHTML = '<p>No recommended issues found.</p>';
            return;
        }
        issues.forEach(issue => {
            const card = document.createElement('div');
            card.className = 'issue-card';
            card.innerHTML = `<h2>${issue.title}</h2>
                              <p>Repository: ${issue.repo} | Status: <span class="${issue.status}">${issue.status}</span></p>`;
            card.addEventListener('click', () => {
                showIssueDetails(issue.id);
            });
            issuesContainer.appendChild(card);
        });
    } catch (error) {
        console.error('Error loading recommended issues:', error);
        issuesContainer.innerHTML = `<p>Error loading recommended issues: ${error.message}</p>`;
    }
}


