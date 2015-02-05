<%@ tag description="NavBar" body-content="scriptless"%>

<%@ attribute name="style" required="false" type="java.lang.String" description="Opcional. Default = nenhum."%>

<nav class="band navbar gradient ${style}">
    <div class="container mini-padding-v">
        <ul class="nav responsive">
            <jsp:doBody />
        </ul>
    </div>
</nav>
