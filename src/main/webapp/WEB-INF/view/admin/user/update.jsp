<%@page contentType="text/html" pageEncoding="UTF-8" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
      <html lang="en">

      <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://getbootstrap.com/docs/5.3/assets/css/docs.css" rel="stylesheet">
        <title>Create Users</title>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
      </head>

      <body>
        <div class="container mt-5">
          <div class="row">
            <div class="col-md-6 col-12 mx-auto">
              <h3>Update a user</h3>
              <hr />
              <form:form method="post" action="/admin/user/update" modelAttribute="newUser">
                <div class="mb-3" style="display: none;">
                  <label class="form-label">Id:</label>
                  <form:input type="number" class="form-control" path="id" />
                </div>
                <div class="mb-3">
                  <label class="form-label">Email:</label>
                  <form:input type="email" class="form-control" path="email" disabled="true" />
                </div>

                <div class="mb-3">
                  <label class="form-label">Phone number:</label>
                  <form:input type="text" class="form-control" path="phone" />
                </div>
                <div class="mb-3">
                  <label class="form-label">Full Name:</label>
                  <form:input type="text" class="form-control" path="fullName" />
                </div>
                <div class="mb-3">
                  <label class="form-label">Address:</label>
                  <form:input type="text" class="form-control" path="address" />
                </div>

                <button type="submit" class="btn btn-warning">Update</button>
              </form:form>
            </div>
          </div>
        </div>
      </body>

      </html>