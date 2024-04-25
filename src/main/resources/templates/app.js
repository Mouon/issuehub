document.addEventListener('DOMContentLoaded', function () {
    fetch('http://localhost:9000/category/list')
    .then(response => response.json())
    .then(categories => {
        const menu = document.getElementById('category-menu');
        let initialLoad = true;
        categories.forEach(category => {
            const menuItem = document.createElement('li');
            menuItem.textContent = category.name;
            menuItem.setAttribute('data-repo', category.name);
            menuItem.addEventListener('click', function() {
                const repo = this.getAttribute('data-repo');
                fetchAndDisplayIssues(repo);
                fetchAndDisplayReadmePreview(repo);
            });
            menu.appendChild(menuItem);
            if (initialLoad) {
                fetchAndDisplayIssues(category.name);
                fetchAndDisplayReadmePreview(category.name);
                initialLoad = false;
            }
        });
    })
    .catch(error => console.error('Error loading categories:', error));
});




async function fetchAndDisplayIssues(categoryName) {
    const issuesContainer = document.getElementById('issues');
    issuesContainer.innerHTML = ''; // Clear previous issues
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
document.getElementById('search-input').addEventListener('input', function(event) {
    if (event.target.value.length > 0) {
        searchIssues(event.target.value);
    } else {
        fetchAndDisplayIssues('defaultCategory'); // 예를 들어, 기본 카테고리나 전체 리스트를 다시 표시
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


