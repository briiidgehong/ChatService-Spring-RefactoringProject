<%@ page language="java" pageEncoding="UTF-8"%>
<!-- 모달창 생성 -->
<%
    String messageContent = null;
    if(session.getAttribute("messageContent") !=null) {
        messageContent = (String) session.getAttribute("messageContent");
    }

    String messageType = null;
    if(session.getAttribute("messageType") !=null) {
        messageType = (String) session.getAttribute("messageType");
    }

    if(messageContent != null) {
%>

<div class="modal fade" id="messageModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="vertical-alignment-helper">
        <div class="modal-dialog vertical-align-center">
            <div class="modal-content <% if(messageType.equals("오류메시지")) out.println("panel-warning"); else out.println("panel-success"); %>">
                <div class="modal-header panel-heading">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">&times;</span>
                        <span class="sr-only">close</span>
                    </button>

                    <h4 class="modal-title">
                        <%=messageType %>
                    </h4>
                </div>
                <div class="modal-body">
                    <%=messageContent %>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" data-dismiss="modal">확인</button>
                </div>
            </div>
        </div>
    </div>
</div>



<script>
    $('#messageModal').modal("show");
</script>
<%
        session.removeAttribute("messageContent");
        session.removeAttribute("messageType");
    }
%>

<div class="modal fade" id="checkModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="vertical-alignment-helper">
        <div class="modal-dialog vertical-align-center">
            <div id="checkType" class="modal-content panel-info">
                <div class="modal-header panel-heading">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">&times</span>
                        <span class="sr-only">close</span>
                    </button>

                    <h4 class="modal-title">
                        확인 메시지
                    </h4>
                </div>
                <div><h5 id="checkMessage" class="modal-body"></h5>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" data-dismiss="modal">확인</button>
                </div>

            </div>
        </div>
    </div>
</div>

<%
    if(userID != null) {
%>
<script type="text/javascript">
    $(document).ready(function(){
        getUnread();
        getInfiniteUnread();
    });
</script>
<%
    }
%>
