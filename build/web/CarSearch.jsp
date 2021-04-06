<%-- 
    Document   : CarSearch
    Created on : Feb 27, 2021, 3:33:31 AM
    Author     : Thuy Linh
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <header>
            <c:if test="${not empty sessionScope.FULLNAME}">
                <font color="blue">Welcome, ${sessionScope.FULLNAME}</font><br><br>
                <form action="logout">
                    <input type="submit" value="Log out" />
                </form>
                <br>
                <c:url var="searchCart" value="search">
                    <c:param name="txtSearchName" value=""/>
                    <c:param name="txtAmountOfCar"  value=""/>
                    <c:param name="rentalDate"   value=""/>
                    <c:param name="returnDate"   value=""/>
                    <c:param name="cboCategory"      value=""/>
                </c:url>
                <a href="${searchCart}" ><font size="4">Car Rental</font></a>
                <br><br>
                <c:url var="searchHistory" value="searchHistory">
                    <c:param name="txtSearchName" value=""/>
                    <c:param name="txtBookingDate"  value=""/>
                </c:url>
                <a href="${searchHistory}" ><font size="2">History</font></a>
                </c:if>
                <c:if test="${ empty sessionScope.FULLNAME}">
                <form action="Login_Page">
                    <input type="submit" value="Log In" />
                </form>
                <br>
                <c:url var="searchCart" value="search">
                    <c:param name="txtSearchName" value=""/>
                    <c:param name="txtAmountOfCar"  value=""/>
                    <c:param name="rentalDate"   value=""/>
                    <c:param name="returnDate"   value=""/>
                    <c:param name="cboCategory"      value=""/>
                </c:url>
                <a href="${searchCart}" ><font size="4">Car Rental</font></a>
                <br><br>
            </c:if>
        </header>

        <form action="search">
            Search <input type="text" name="txtSearchName" value="${param.txtSearchName}" />
            Category <select name="cboCategory">
                <option>ALL</option>
                <c:forEach var="category" items="${sessionScope.CATEGORY}">
                    <option value="${category}" ${category == param.cboCategory ? 'selected="selected"' : ''}>${category}</option>
                </c:forEach>
            </select>
            Amount of car <input type="number" name="txtAmountOfCar" value="${param.txtAmountOfCar}" min="1"/>
            Rental date <input type="date" name="rentalDate" value="${param.rentalDate}" min="${sessionScope.CURRENT_DATE}"/>
            Return date <input type="date" name="returnDate" value="${param.returnDate}" min="${sessionScope.CURRENT_DATE}"/>
            <input type="submit" value="Search" />
        </form>
        <c:if test="${not empty requestScope.DATE_ERROR}"> <font color="red"> ${requestScope.DATE_ERROR}</font>  </c:if>
        <c:set var="listSearch" value="${requestScope.SEARCH_LIST}"/>
        <c:if test="${not empty listSearch}">
            <table border="1">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Car Name</th>
                        <th>Color</th>
                        <th>Year</th>
                        <th>Category</th>
                        <th>Price</th>
                        <th>Quantity Available</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${listSearch}" varStatus="counter">
                    <form action="addToCart">
                        <tr>
                            <td>${counter.count}</td>
                        <input type="hidden" name="txtCarID" value="${item.carID}" />
                        <td>${item.carName}</td> 
                        <td>${item.color}</td> 
                        <td>${item.year}</td> 
                        <td>${item.category}</td> 
                        <td>${item.price}$</td> 
                        <c:forEach var="quantityAvailable" items="${requestScope.QuantityAvailable}">
                            <c:if test="${quantityAvailable.key eq item.carID}">
                                <td>
                                    ${quantityAvailable.value}
                                </td> 
                            </c:if>
                        </c:forEach>

                        <td> <input type="submit" value="Add to cart" /> </td>
                        </tr>
                    </form>
                </c:forEach>
            </tbody>
        </table>

    </c:if>

    <c:set var="currentPage" value="${requestScope.currentPage}"/>
    <c:set var="pageSize" value="${requestScope.PAGESIZE}"/>
    <table border="1">
        <tr>
            <c:forEach var="pageID" begin="1" end="${pageSize}">
                <c:choose>
                    <c:when test="${currentPage eq pageID}">
                        <td>
                            ${pageID}
                        </td>
                    </c:when>
                    <c:otherwise>
                        <c:url var="nextPage" value="search">
                            <c:param name="page"    value="${pageID}"/>
                            <c:param name="txtSearchName"    value="${param.txtSearchName}"/>
                            <c:param name="txtAmountOfCar"  value="${param.txtAmountOfCar}"/>
                            <c:param name="rentalDate"  value="${param.rentalDate}"/>
                            <c:param name="returnDate"  value="${param.returnDate}"/>
                            <c:param name="cboCategory"      value="${param.cboCategory}"/>
                        </c:url>
                        <td>
                            <a href="${nextPage}">${pageID}</a>
                        </td>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </tr>
    </table>
    <c:if test="${not empty sessionScope.FULLNAME}">
        <form action="viewCart">
            <input type="submit" value="View Your Cart" />
        </form>
    </c:if>

</body>
</html>
