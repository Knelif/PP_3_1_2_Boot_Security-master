//////////////! Global variables //////////////
// Create form elements
const createFormElements = {
    form: $("#createUserForm"),
    name: $("#createName"),
    lastname: $("#createLastName"),
    age: $("#createAge"),
    email: $("#createEmail"),
    password: $("#createPassword"),
    role: $("#createRole")
}

// Edit form elements
const editFormElements = {
    form: $("#editForm"),
    id: $("#editId"),
    name: $("#editName"),
    lastname: $("#editLastName"),
    age: $("#editAge"),
    email: $("#editEmail"),
    password: $("#editPassword"),
    role: $("#editRole"),
    submitButton: $("#editButtonSubmit")
}

// Delete form elements
const deleteFormElements ={
    form: $("#deleteForm"),
    id: $("#deleteId"),
    name: $("#deleteName"),
    lastname: $("#deleteLastName"),
    age: $("#deleteAge"),
    email: $("#deleteEmail"),
    role: $("#deleteRole"),
    submitButton: $("#deleteButtonSubmit")
}
// csrf token
const csrfToken = $("[name='csrf_token']").attr("content");



//////////* Event listeners ////////////////////
    $(document).ready(function() {
        
    });

    //////////? Listeners for content preparing /////////////////

    $("#home-tab").on("click", fillUsersTable);

    // modal DeleteForm listener for fill form
    $("#deleteModal").on('show.bs.modal', function(event) {
        const triggerButton = $(event.relatedTarget);
        const userId = triggerButton.data("id");
        fillDeleteUserForm(userId);
    });

    // modal EditForm listener for fill form
    $("#editModal").on("show.bs.modal", function(event) {
        const triggerButton = $(event.relatedTarget);
        const userId = triggerButton.data("id");
        fillEditUserForm(userId);
    });


    //////////** Submit listeners ///////////////
    // Delete submit button listener    
    $(deleteFormElements.form).on("submit", function(event) {
        event.preventDefault();
        $.ajax({
            url: "/api/users/delete/" + deleteFormElements.submitButton.data("id"),
            headers: {
                "X-CSRF-TOKEN": csrfToken,
                "Content-Type": "application/json"
            },
            method: "DELETE",
            success: function(data) {
                fillUsersTable();
                $('#deleteModal').modal('hide');
            },
            error: function(err) {
                fillUsersTable();
                $('#deleteModal').modal('hide');
                logError(err);
            }
        });
    });
    // Create submit button listener
    $(createFormElements.form).on("submit", function(event) {
        event.preventDefault();
        $(".is-invalid").removeClass("is-invalid");
        $(".invalid-feedback").remove();
        let roles = [];
        $("#createRole option:selected").each(function(value) {
            let role = {id: $(this).val(), name: $(this).data("role")};
            roles.push(role);
        })
        $.ajax({
            url: "/api/users/create",
            headers: {            
                "X-CSRF-TOKEN": csrfToken,
                "Content-Type": "application/json"
            },
            method: "POST",
            data: JSON.stringify({
                name: createFormElements.name.val(),
                lastname: createFormElements.lastname.val(),
                age: createFormElements.age.val(),
                email: createFormElements.email.val(),
                password: createFormElements.password.val(),
                role: roles
            }),
            success: function(data) {
                createFormElements.form[0].reset();
            },
            error: function(err) {
                logError(err);
                let errors = err.responseJSON.errors;
                $.each(errors, function(key, value) {
                    createFormElements[key].addClass("is-invalid");
                    value.forEach(function(error) {
                        createFormElements[key].after("<span class='invalid-feedback'>" + error + "</span>");
                    })
                });

            }
        });
    });

    // Edit submit button listener 
    $(editFormElements.form).on("submit", function(event) {
        $("#editForm .is-invalid").removeClass("is-invalid");
        event.preventDefault();
        let roles = [];
        $("#editRole option:selected").each(function(value) {
            let role = {id: $(this).val(), name: $(this).data("role")};
            roles.push(role);
        })
        $.ajax({
            url: "/api/users/update/" + editFormElements.submitButton.data("id"),
            headers: {
                "X-CSRF-TOKEN": csrfToken,
                "Content-Type": "application/json"
            },
            method: "PUT",
            data: JSON.stringify({
                id: editFormElements.id.val(),
                name: editFormElements.name.val(),
                lastname: editFormElements.lastname.val(),
                age: editFormElements.age.val(),
                email: editFormElements.email.val(),
                password: editFormElements.password.val(),
                role: roles
            }),
            success: function(data) {
                fillUsersTable();
                $("#editModal").modal('hide');
                editFormElements.form[0].reset();
            },
            error: function(err) {
                fillUsersTable();
                logError(err);
                let errors = err.responseJSON.errors;
                $.each(errors, function(key, value) {
                    editFormElements[key].addClass("is-invalid");
                });
                
            }
        })
    });

//////////? Filling functions /////////////////

    // Fill users table
    function fillUsersTable() {
        const table = $("#usersTable");
        try {
            $.getJSON("/api/users/")
                .then(data => {
                    table.find("tbody").empty();
                    $.each(data, function (index, user) {
                        const row = $("<tr>").appendTo(table.find("tbody"));
                        const idCell = $("<th scope='row'>").addClass("align-middle").text(user.id).appendTo(row);
                        const nameCell = $("<td>").addClass("align-middle").text(user.name).appendTo(row);
                        const lastNameCell = $("<td>").addClass("align-middle").text(user.lastname).appendTo(row);
                        const ageCell = $("<td>").addClass("align-middle").text(user.age).appendTo(row);
                        const emailCell = $("<td>").addClass("align-middle").text(user.email).appendTo(row);
                        const roleCell = $("<td>").addClass("align-middle").text(user.inlineRoles).appendTo(row);
                        const editCell = $("<td>").addClass("text-center").appendTo(row);
                        const deleteCell = $("<td>").addClass("text-center").appendTo(row);

                        editCell.html("<button class='btn btn-primary' id='editButton' data-bs-toggle='modal' data-bs-target='#editModal' data-id=" + user.id + ">Edit</button>");
                        deleteCell.html("<button class='btn btn-danger' id='deleteButton' data-bs-toggle='modal' data-bs-target='#deleteModal' data-id=" + user.id + ">Delete</button>");
                    });
                });
        } catch (e) {
            logError(err);
        }
    }

    // Fill edit form
    function fillEditUserForm(id) {
        $("#editForm .is-invalid").removeClass("is-invalid");
        $.ajax({
            url: "/api/users/" + id,
            method: "GET",
            dataType: "json",
            success: function(user) {
                editFormElements.id.val(user.id);
                editFormElements.submitButton.data("id", user.id);
                editFormElements.name.val(user.name);
                editFormElements.lastname.val(user.lastname);
                editFormElements.age.val(user.age);
                editFormElements.email.val(user.email);
                editFormElements.password.val(user.password);
                $.each(user.role, function (index, role) {
                    $("#editRole option[value=" + role.id + "]").prop("selected", true);
                });
            },
            error: function(err) {
                logError(err);
            }
        });
    }

    // Fill delete form
    function fillDeleteUserForm(id) {
        $.ajax({
            url: "/api/users/" + id,
            method: "GET",
            dataType: "json",
            success: function(user) {
                deleteFormElements.id.val(user.id);
                deleteFormElements.name.val(user.name);
                deleteFormElements.lastname.val(user.lastname);
                deleteFormElements.age.val(user.age);
                deleteFormElements.email.val(user.email);
                $.each(user.role, function(index, role) {
                    $("#deleteRole option[value=" + role.id + "]").prop("selected", true);
                });
                $(deleteFormElements.submitButton).data("id", user.id);
            },
            error: function(err) {
                $("#deleteModal").modal("hide");
                logError(err);
            }
        });
    }

////////////////! Error logging /////////////////
function logError(err) {
    console.log(err.responseJSON);
}






