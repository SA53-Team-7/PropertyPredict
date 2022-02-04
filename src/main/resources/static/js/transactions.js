var currentDate = new Date();
var datapoints = [];
var labels = [];
var priceTrend;

window.onload = function() {
	getDataset();
	getPriceTrendChart();
}

function filterTable() {	
	datapoints = [];
	labels = [];
	
	var floorDropdown, floorFilter, table, rows, areaDropdown, areaFilter, periodDropdown, periodFilter, txnDate;
	 
	floorDropdown = document.getElementById("floor-dropdown");
	areaDropdown = document.getElementById("area-dropdown");
	periodDropdown = document.getElementById("period-dropdown");
	
	floorFilter = floorDropdown.value;
	areaFilter = areaDropdown.value;
	periodFilter = periodDropdown.value;
	
	var startYear = getStartYear(periodFilter);
	var currMonth = currentDate.getMonth().toString();
	var startPeriodDt = new Date(startYear, currMonth, 15);
	
	table = document.getElementById("property-txn-list")
	rows = table.getElementsByTagName("tr");
	
	for (let row of rows) {
		var col1 = row.getElementsByTagName("td").item(1);
		var floorRange = (col1 === null ? col1 : col1.innerText);
		
		var col2 = row.getElementsByTagName("td").item(2);
		var area = (col2 === null ? col2 : col2.innerText);
		
		var col0 = row.getElementsByTagName("td").item(0);
		
		if (col0 !== null) {
			var month = col0.innerText.substring(0,1) == '0' ? col0.innerText.substring(1,2) : col0.innerText.substring(0,2);
			var year = '20'.concat(col0.innerText.substring(3));		
			txnDate = new Date(year, parseInt(month) - 1, 15);
		}
				
		if (row.rowIndex === 0 || 
			(floorFilter === floorRange || floorFilter === "All") && 
			(areaFilter === area || areaFilter === "All") && 
			(txnDate >= startPeriodDt ||periodFilter == "All")) {
			row.style.display = "";
			
			if (row.rowIndex !== 0) {
				datapoints.push(Number(row.getElementsByTagName("td").item(3).innerText));
				labels.push(col0.innerText);
			}
		} else {
			row.style.display = "none";
		}
	}
	
	getPriceTrendChart();
}

function getStartYear(period) {
	if (period != "All") {
		var startYear = (currentDate.getFullYear() - period).toString();
		return startYear;
	} 
	return null;	
}

function getDataset() {
	var table = document.getElementById("property-txn-list")
	var rows = table.getElementsByTagName("tr");
	for (let row of rows) {
		if (row.rowIndex != 0) {
			datapoints.push(Number(row.getElementsByTagName("td").item(3).innerText));
			labels.push(row.getElementsByTagName("td").item(0).innerText);
		}
	}
}

function getPriceTrendChart() {
	if (priceTrend)
		priceTrend.destroy();
	
	var data = {
		labels: labels,
		datasets:[{
			label: 'Price trend',
			backgroundColor:'rgb(255,99,132)',
			borderColor: 'rgb(255,99,132)',
			data: datapoints,	
		}]
	};
	
	var config = {
		type: 'line',
		data: data,
		options: {}
	}
	
	priceTrend = new Chart(document.getElementById("price-trend-canvas"), config)
}
