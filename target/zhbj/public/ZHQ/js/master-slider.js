var MasterSliderShowcase2 = function () {

    return {

        //Master Slider
        initMasterSliderShowcase2: function () {
		    var slider = new MasterSlider();

		    slider.control('arrows');
		    slider.control('thumblist' , {autohide:false ,dir:'h',arrows:false, align:'bottom', width:100, height:75, margin:5, space:5});

		    slider.setup('masterslider' , {
		        width:550,
		        height:412,
		        space:5,
		        view:'fade'
		    });
        }

    };

}();
