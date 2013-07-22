if (typeof jQuery !== 'undefined') {
	(function($) {
		$('#spinner').ajaxStart(function() {
			$(this).fadeIn();
		}).ajaxStop(function() {
			$(this).fadeOut();
		});

        $('.close').click(function(e){
            e.preventDefault();
            $(this).parent(".alert-box").fadeOut();
            return false;
        });
	})(jQuery);
}  else{
    alert('errorrrrrrr');
}
$(function(){
   // $("#test").attr("data-bind","value:values");
    
var initialResponse =[
    {
        value :""
    }
];

var responseModel=function(responses){
    var self = this; 
    self.responses = ko.observableArray(ko.utils.arrayMap(responses, function(response) {
        return { value: response.value};
    }));
    self.addResponse= function(){
        if(responses && responses.lenght >0)
            self.lastValue(responses[responses.lenght-1]);
        $("#test").click();
      self.values(JSON.stringify(ko.toJS(self.responses), null, 2));
      self.responses.push(
          {
            value : ""
          });  
          
    };
     self.removeResponse = function(response) {
        self.responses.remove(response);
    };
    self.values = ko.observable("");
    self.lastValue = ko.observable("");
    self.save = function() {
        self.values(JSON.stringify(ko.toJS(self.responses), null, 2));
        alert(self.values);
    };
};
ko.applyBindings(new responseModel(initialResponse));
});




