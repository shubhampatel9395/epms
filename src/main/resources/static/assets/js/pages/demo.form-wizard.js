$(function () { 
    "use strict";
      
    $("#rootwizard").bootstrapWizard({ 
        onNext: function (t, r, a) { 
            var o = $($(t).data("targetForm")); 
            if (o && (o.addClass("was-validated"), !1 === o[0].checkValidity())) return event.preventDefault(), event.stopPropagation(), !1 
        } ,
        onTabShow: function (t, r, a) { 
            var o = (a + 1) / r.find("li").length * 100; 
            $("#rootwizard").find(".bar").css({ width: o + "%" }) 
        }
    })
});