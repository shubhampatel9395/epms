var briteChartApp = window.briteChartApp || {};
var da;
$.ajax({
	type: "GET",
	dataType: 'json',
	url: "/admin/getChartData/",
	success: function(result) {
		da = JSON.parse(JSON.stringify(result));
		console.log(da);
	}
});

!(function(i, e) {
	"use strict";
	var c = [
		"#727cf5",
		"#0acf97",
		"#6c757d",
		"#fa5c7c",
		"#ffbc00",
		"#39afd1",
		"#e3eaef",
		"#E99EEE",
		"#ff679b",
		"#000000",
		"#FFFB00",
		"#FF0000"
	];

	(e.createDonutChart = function(e, t) {
		var a = i(e).data("colors"),
			l = a ? a.split(",") : c.concat(),
			n = britecharts.donut(),
			u = britecharts.legend();
		u.width(250).height(200).colorSchema(l).numberFormat("s"),
			n
				.height(300)
				.highlightSliceById(3)
				.colorSchema(l)
				.hasFixedHighlightedSlice(!0)
				.internalRadius(80)
				.on("customMouseOver", function(e) {
					u.highlight(e.data.id);
				})
				.on("customMouseOut", function() {
					u.clearHighlight();
				}),
			d3.select(e).datum(t).call(n);
		d3.select(".legend-chart-container").datum(t).call(u);
	}),
		i(function() {
			// var a;
			console.log(da);
			/*
			var a = [
				{ name: "Shiny11", id: 1, quantity: 86, percentage: 5 },
				{ name: "Blazing", id: 2, quantity: 300, percentage: 18 },
				{ name: "Dazzling", id: 3, quantity: 276, percentage: 16 },
				{ name: "Radiant", id: 4, quantity: 195, percentage: 11 },
				{ name: "Sparkling", id: 5, quantity: 36, percentage: 2 },
			];
			*/
			function u() {
				d3.selectAll(".donut-chart").remove(),
					d3.selectAll(".britechart-legend").remove(),
					0 < i(".donut-container").length &&
					briteChartApp.createDonutChart(".donut-container", da);
			}
			i(window).on("resize", function(e) {
				e.preventDefault(), setTimeout(u, 200);
			}),
				u();
		});
})(jQuery, briteChartApp);
