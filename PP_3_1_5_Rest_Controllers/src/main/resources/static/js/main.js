// Заголовок
document.addEventListener("DOMContentLoaded", async function header() {
    let html = ``;
    const infoUser = document.querySelector('#info');
    let user = await fetch('api/user');
    let json = await user.json();
    html += `
        <span class="fw-bold">${json.email}</span>
        <span>with roles</span>
        <span>${json.roles.map(role => role.name.replace('ROLE_', '')).join(' ')}</span>
    `;
    infoUser.innerHTML = html;
})


// Навигационная панель (вкладки 'Admin' и 'User')
document.addEventListener("DOMContentLoaded", async function navigation() {
    const adminTab = document.querySelector('#admin-tab');
    const userTab = document.querySelector('#user-tab');
    const adminContent = document.querySelector('#admin');
    const userContent = document.querySelector('#user');

    let userResponse = await fetch('api/user');
    let user = await userResponse.json();

    let roles = user.roles.map(role => role.name);
    const isAdmin = roles.includes('ROLE_ADMIN');

    function updateActiveTab(tab) {
        document.querySelectorAll('#myTab .nav-link').forEach(t => {
            t.classList.remove('active');
            t.setAttribute('aria-selected', 'false');
        });
        tab.classList.add('active');
        tab.setAttribute('aria-selected', 'true');
    }

    if (isAdmin) {
        adminTab.style.display = 'block';
        userTab.style.display = 'block';
        adminContent.style.display = 'block';
        userContent.style.display = 'none';
        updateActiveTab(adminTab);
        document.querySelector('#admin-tab').classList.add('active');
        document.querySelector('#admin-tab').setAttribute('aria-selected', 'true');
        document.querySelector('#admin').classList.add('show', 'active');

        userTab.addEventListener('click', function() {
            userContent.style.display = 'block';
            adminContent.style.display = 'none';
        });

        adminTab.addEventListener('click', function() {
            adminContent.style.display = 'block';
            userContent.style.display = 'none';
        });
    } else {
        adminTab.style.display = 'none';
        userTab.style.display = 'block';
        adminContent.style.display = 'none';
        userContent.style.display = 'block';
        updateActiveTab(userTab);
        document.querySelector('#user-tab').classList.add('active');
        document.querySelector('#user-tab').setAttribute('aria-selected', 'true');
        document.querySelector('#user').classList.add('show', 'active');
    }
})


// Панели 'Admin Panel' и 'User information-page'
document.addEventListener("DOMContentLoaded", async function panels() {
    const userInfoTableBody = document.querySelector('#userInfoTableBody');
    const usersTableBody = document.querySelector('#usersTableBody');
    const rolesSelect = document.querySelector('select[name="roles"]');
    const addNewUserBtn = document.getElementById('addNewUser');
    const editModal = new bootstrap.Modal(document.getElementById('editModal'));
    const deleteModal = new bootstrap.Modal(document.getElementById('deleteModal'));
    let userToEditId = null;
    let userToDeleteId = null;


    // Информация о текущем пользователе
    async function loadCurrentUser() {
        let response = await fetch('api/user');
        if (response.ok) {
            let user = await response.json();
            let roles = user.roles.map(role => role.name.replace('ROLE_', '')).join(' ');
            userInfoTableBody.innerHTML = `
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.age}</td>
                        <td>${user.email}</td>
                        <td>${roles}</td>
                    </tr>
                `;
        }
    }


    // Все пользователи
    async function loadAllUsers() {
        let response = await fetch('api/admin/users');
        if (response.ok) {
            let users = await response.json();
            usersTableBody.innerHTML = users.map(user => {
                let roles = user.roles.map(role => role.name.replace('ROLE_', '')).join(' ');
                return `
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.age}</td>
                        <td>${user.email}</td>
                        <td>${roles}</td>
                        <td>
                            <button type="button" class="btn btn-info btn-sm" data-bs-toggle="modal" data-bs-target="#editModal" data-user-id="${user.id}">Edit</button>
                        </td>
                        <td>
                            <button type="button" class="btn btn-danger btn-sm" data-bs-toggle="modal" data-bs-target="#deleteModal" data-user-id="${user.id}">Delete</button>
                        </td>
                    </tr>
                `;
            }).join('');

            // Обработчик "Edit" в таблице
            document.querySelectorAll('button[data-bs-target="#editModal"]').forEach(button => {
                button.addEventListener('click', function() {
                    userToEditId = this.getAttribute('data-user-id');
                    populateEditModal(userToEditId);
                });
            });

            // Обработчик "Delete" в таблице
            document.querySelectorAll('button[data-bs-target="#deleteModal"]').forEach(button => {
                button.addEventListener('click', function() {
                    userToDeleteId = this.getAttribute('data-user-id');
                    populateDeleteModal(userToDeleteId);
                });
            });
        }
    }


    // Все роли
    async function loadRoles() {
        let response = await fetch('api/admin/roles');
        if (response.ok) {
            let roles = await response.json();
            rolesSelect.innerHTML = roles.map(role => `<option value="${role.id}">${role.name.replace('ROLE_', '')}</option>`).join('');
        }
    }


    // Данные пользователя в модальном окне для редактирования
    async function populateEditModal(userId) {
        let userResponse = await fetch(`/api/admin/user?id=${userId}`);
        if (userResponse.ok) {
            let user = await userResponse.json();
            document.getElementById('editUserId').value = user.id;
            document.getElementById('editUserFirstName').value = user.firstName;
            document.getElementById('editUserLastName').value = user.lastName;
            document.getElementById('editUserAge').value = user.age;
            document.getElementById('editUserEmail').value = user.email;

            let rolesResponse = await fetch('api/admin/roles');
            if (rolesResponse.ok) {
                let roles = await rolesResponse.json();
                let rolesSelect = document.getElementById('editUserRole');
                rolesSelect.innerHTML = roles.map(role => {
                    let selected = user.roles.some(userRole => userRole.name === role.name) ? 'selected' : '';
                    return `<option value="${role.id}" ${selected}>${role.name.replace('ROLE_', '')}</option>`;
                }).join('');
            }

            editModal.show();
        }
    }

    // Данные пользователя в модальном окне для удаления
    async function populateDeleteModal(userId) {
        let response = await fetch(`/api/admin/user?id=${userId}`);
        if (response.ok) {
            let user = await response.json();
            document.getElementById('deleteUserId').value = user.id;
            document.getElementById('deleteUserFirstName').value = user.firstName;
            document.getElementById('deleteUserLastName').value = user.lastName;
            document.getElementById('deleteUserAge').value = user.age;
            document.getElementById('deleteUserEmail').value = user.email;

            const rolesSelect = document.getElementById('deleteUserRole');
            rolesSelect.innerHTML = user.roles.map(role =>
                `<option value="${role.id}">${role.name.replace('ROLE_', '')}</option>`
            ).join('');

            deleteModal.show();
        }
    }


    // Редактирование пользователя
    async function editUser() {
        const updatedUser = {
            id: document.getElementById('editUserId').value,
            firstName: document.getElementById('editUserFirstName').value,
            lastName: document.getElementById('editUserLastName').value,
            age: document.getElementById('editUserAge').value,
            email: document.getElementById('editUserEmail').value,
            roles: Array.from(document.getElementById('editUserRole').selectedOptions).map(option => option.value)
        };

        let response = await fetch('/api/admin/update', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(updatedUser)
        });

        if (response.ok) {
            await loadAllUsers();
            editModal.hide();
        }
    }

    // Обработчик "Edit" в модальном окне
    document.getElementById('confirmEdit').addEventListener('click', editUser);

    // Удаление пользователя
    async function deleteUser() {
        let response = await fetch('/api/admin/delete?id=' + userToDeleteId, {
            method: 'DELETE'
        });

        if (response.ok) {
            await loadAllUsers();
            deleteModal.hide();
        }
    }

    // Обработчик "Delete" в модальном окне
    document.getElementById('confirmDelete').addEventListener('click', deleteUser);


    // Добавление нового пользователя
    async function addNewUser() {
        const firstName = document.getElementById('firstName').value;
        const lastName = document.getElementById('lastName').value;
        const age = document.getElementById('age').value;
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;
        const roles = Array.from(document.querySelector('select[name="roles"]').selectedOptions).map(option => option.value);

        const newUser = {
            firstName,
            lastName,
            age,
            email,
            password,
            roles
        };

        let response = await fetch('/api/admin/create', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newUser)
        });

        if (response.ok) {
            await loadAllUsers();

            const tab = new bootstrap.Tab(document.getElementById('users-tab'));
            tab.show();

            document.getElementById('firstName').value = '';
            document.getElementById('lastName').value = '';
            document.getElementById('age').value = '';
            document.getElementById('email').value = '';
            document.getElementById('password').value = '';
            document.querySelector('select[name="roles"]').selectedIndex = -1;
        }
    }

    // Обработчик "Add new user"
    addNewUserBtn.addEventListener('click', addNewUser);


    // Загрузка данных
    await loadCurrentUser();
    await loadAllUsers();
    await loadRoles();
})
