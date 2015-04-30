<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.2/handlebars.min.js"></script>



<div id="final"></div>



<script id="entry-template" type="text/x-handlebars-template">

  <div class="entry">

    <h1>{{title}}</h1>

    <div class="body">

      {{body}}

    </div>

  </div>

</script>



<script>

    var source   = $("#entry-template").html();

    var template = Handlebars.compile(source);

    var context = {title: "My New Post", body: "This is my first post!"};

    var html    = template(context);



    $("#final").html(html);

</script>