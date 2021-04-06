<%-- 
    Document   : SearchHistory
    Created on : Mar 5, 2021, 1:04:09 PM
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
                <font color="blue">Welcome, ${sessionScope.FULLNAME}</font></br>
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
                </c:if>
        </header>
        <br><br>
        <form action="searchHistory">
            Name Car: <input type="text" name="txtSearchName" value="${param.txtSearchName}" /></br>
            Booking Date: <input type="date" name="txtBookingDate" value="${param.txtBookingDate}" /></br>
            <input type="submit" value="Search" />        
        </form>
        <c:set var="listHistory" value="${requestScope.HISTORY}"/>
        <c:if test="${not empty listHistory}">
            <c:forEach var="booking" items="${listHistory}">
                </br>
            <td colspan="7">BookingID: ${booking.key}</td>
            <table border="1">
                <thead>
                    <tr>
                        <th>NO</th>
                        <th>Car Name</th>
                        <th>Quantity</th>
                        <th>Price</th>
                        <th>Rental Date</th>
                        <th>Return Date</th>
                        <th>Booking Date</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="historyDTO" items="${booking.value}" varStatus="counter">
                        <tr>

                            <th>${counter.count}</th>
                            <th>${historyDTO.carName}</th>
                            <th>${historyDTO.quantity}</th>
                            <th>${historyDTO.price}</th>
                            <th>${historyDTO.rentalDate}</th>
                            <th>${historyDTO.returnDate}</th>
                            <th>${historyDTO.bookingDate}</th>
                        </tr>
                    </c:forEach>
                    <c:set var="totalPrice" value="${requestScope.TOTALPRICE}"/>
                    <c:forEach var="total" items="${totalPrice}">
                        <c:if test="${total.key eq booking.key}">
                        <td colspan="7"> 
                            Total: ${total.value}$ 
                            <c:forEach var="status" items="${requestScope.STATUS_BILL}">
                                <c:if test="${status.key eq booking.key}">
                                    <c:if test="${status.value eq 'Active'}">
                                        <form action="deleteHistory">
                                            <input type="hidden" name="bookingID" value="${booking.key}" />
                                            <input type="hidden" name="txtSearchName" value="${param.txtSearchName}" /></br>
                                            <input type="hidden" name="txtBookingDate" value="${param.txtBookingDate}" /></br>
                                            <input type="submit" value="Delete" />
                                        </form>
                                    </c:if>
                                </c:if>
                            </c:forEach>                          
                        </td>
                    </c:if>
                </c:forEach>
            </tbody>
        </table>
    </c:forEach>
</c:if>
<c:if test="${not empty requestScope.STATUS_DELETE}">
    <font color="red"> ${requestScope.STATUS_DELETE}</font>
</c:if>
<c:if test="${empty listHistory}">
    No History.
</c:if>
</body>
</html>
