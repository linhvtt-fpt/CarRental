<%-- 
    Document   : ViewCart
    Created on : Mar 2, 2021, 10:21:51 AM
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
                <c:url var="searchCart" value="search">
                    <c:param name="txtSearchName" value=""/>
                    <c:param name="txtAmountOfCar"  value=""/>
                    <c:param name="rentalDate"   value=""/>
                    <c:param name="returnDate"   value=""/>
                    <c:param name="cboCategory"      value=""/>
                </c:url>
                <a href="${searchCart}" ><font size="4">Car Rental</font></a>
                <br>
                <c:url var="searchHistory" value="searchHistory">
                    <c:param name="txtSearchName" value=""/>
                    <c:param name="txtBookingDate"  value=""/>
                </c:url>
                <a href="${searchHistory}" ><font size="2">History</font></a>
                </c:if>
        </header>
        <br>
        <font size="4"> Your Cart</font>
        <br>
        <style>
            p.right {
                text-align: right;
            }
        </style>
        <c:set var="cart" value="${sessionScope.CUSTCART}"/>
        <c:if test="${not empty cart.items}">
            <table border="1">
                <thead>
                    <tr>
                        <th>NO.</th>
                        <th>Name</th>
                        <th>Category</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Rental Date</th>
                        <th>Return Date</th>
                        <th>Total</th>
                        <th>Choose</th>
                    </tr>
                </thead>
                <form action="dispatch">
                    <tbody>
                        <c:forEach var="listItems" items="${cart.items}" varStatus="counter">
                            <tr>
                                <td>
                                    ${counter.count}
                                </td>
                                <td>
                                    ${listItems.value.carName} <input type="hidden" name="key" value="${listItems.key}" />
                                </td>
                                <td>
                                    ${listItems.value.category}
                                </td>
                                <td>
                                    ${listItems.value.price}$

                                </td>
                                <td>
                                    <input type="number" name="txtQuantity" min="1" value="${listItems.value.quantity}"/>
                                </td>
                                <td>
                                    ${listItems.value.rentalDate}

                                </td>
                                <td>
                                    ${listItems.value.returnDate}                                    
                                </td>
                                <td>
                                    ${listItems.value.total*listItems.value.quantity}$
                                </td>
                                <td>
                                    <input type="checkbox" name="chkBox" value="${listItems.key}" />
                                </td>
                            </tr>
                        </c:forEach>
                        <c:set var="total" value="${sessionScope.TOTAL}"/>
                        <c:if test="${not empty total}">
                        <td colspan="8">
                            <p class="right">Total: ${total}$</p> 
                        </td>
                    </c:if>
                    <tr>
                        <td colspan="8">
                            <input type="submit" value="Remove" name="btnAction" onclick="return confirm('Are you want to REMOVE items from your cart?')"/>
                            <input type="submit" value="Check out" name="btnAction" />
                            <input type="submit" value="Update Cart" name="btnAction" />
                        </td>
                    </tr>
                    </tbody>
                    <font color="blue" size="3"><strong>DISCOUNT</strong></font>
                    <select name="cboDiscount">
                        <option>Choose discount code</option>
                        <c:forEach var="discount" items="${sessionScope.DISCOUNT_LIST}">
                            <option value="${discount.discountID}">${discount.value}% off (Expiry Date: ${discount.expiryDate})</option>

                        </c:forEach>
                    </select>
                </form>
                <c:if test="${not empty requestScope.ERROR_UPDATE}"><font color="red">${requestScope.ERROR_UPDATE}</font></c:if>
                <c:if test="${not empty requestScope.ERROR}"><font color="red">${requestScope.ERROR}</font></c:if>
                </table>
        </c:if>
        </br>

        <c:if test="${empty cart.items}">
            No item!

        </c:if>
        <form action="dispatch">
            <input type="submit" value="Add Items To Your Cart" name="btnAction" />
        </form>
    </body>
</html>
