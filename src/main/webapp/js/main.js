

$(function() {
    $('#fileupload').fileupload({
        dataType: 'json',
        done: function(e, data) {
            //alert("backkkkkkkkkkkk")
          
            //$("tr:has(td)").remove();
            $.each(data.result, function(index, file) {
                $("#img").attr("src", "/RadioServer" + file.path);
                $(PrimeFaces.escapeClientId('form:image')).val(file.path);
            });
        },
        progressall: function(e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#progress .bar').css(
                    'width',
                    progress + '%'
                    );
        },
        dropZone: $('#dropzone')
    });
});