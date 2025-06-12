<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="/includes/header.jsp">
    <jsp:param name="title" value="Liste des Consignataires"/>
</jsp:include>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

<style>
    .card {
        margin-top: 20px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }

    .table-responsive {
        margin-top: 20px;
    }

    .btn-group {
        display: flex;
        gap: 5px;
    }

    .btn {
        white-space: nowrap;
    }

    .alert {
        margin-top: 20px;
    }

    .fa-info-circle {
        margin-right: 5px;
    }
</style>

<div class="container">
    <div class="row">
        <div class="col-12">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2><i class="fas fa-users"></i> Liste des Consignataires</h2>
                <a href="<c:url value='/consignataire/new'/>" class="btn btn-primary">
                    <i class="fas fa-plus"></i> Nouveau Consignataire
                </a>
            </div>

            <div class="card">
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-striped table-hover">
                            <thead class="table-dark">
                                <tr>
                                    <th>ID</th>
                                    <th>Raison Sociale</th>
                                    <th>Adresse</th>
                                    <th>Téléphone</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="consignataire" items="${consignataires}">
                                    <tr>
                                        <td>${consignataire.idConsignataire}</td>
                                        <td>${consignataire.raisonSociale}</td>
                                        <td>${consignataire.adresse}</td>
                                        <td>${consignataire.telephone}</td>
                                        <td>
                                            <div class="btn-group" role="group">
                                                <a href="<c:url value='/consignataire/view/${consignataire.idConsignataire}'/>"
                                                   class="btn btn-sm btn-info"><i class="fas fa-eye"></i> Voir</a>
                                                <a href="<c:url value='/consignataire/edit/${consignataire.idConsignataire}'/>"
                                                   class="btn btn-sm btn-warning"><i class="fas fa-edit"></i> Modifier</a>
                                                <a href="<c:url value='/consignataire/delete/${consignataire.idConsignataire}'/>"
                                                   class="btn btn-sm btn-danger"
                                                   onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce consignataire ?')">
                                                    <i class="fas fa-trash"></i> Supprimer</a>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>

                    <c:if test="${empty consignataires}">
                        <div class="alert alert-info text-center">
                            <i class="fas fa-info-circle"></i> Aucun consignataire trouvé.
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/includes/footer.jsp"/>
