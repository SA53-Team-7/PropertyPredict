var amt, tenure, interest
var principalResult, interestResult
var doughnutChart;

function validateData() {
	
	document.getElementById("amt-error").innerHTML = ""
	document.getElementById("tenure-error").innerHTML = ""
	document.getElementById("int-error").innerHTML = ""
	
	amt = Number(document.getElementById("loan-amt").value)
	tenure = Number(document.getElementById("loan-tenure").value)
	interest = Number(document.getElementById("int-rate").value)
	
	if (amt > 0 && (tenure > 0 && tenure < 36) && interest > 0) {
		sendRequest()
	} else {
		identifyErrors()
	}
}

function sendRequest() {
	// Send GET request to REST Controller
	var data = {
		"loan": amt,
		"tenure": tenure,
		"interest": interest
	}
	
	$.ajax({
		type: 'GET',
		url: "http://localhost:8080/api/mortgage",
		data: data,
		contentType : 'text/plain',
		success: function(result){
			var obj = JSON.parse(result)
/*    		document.getElementById("monthlyPayment").innerHTML = obj.payment
			document.getElementById("principal").innerHTML = obj.principal
			document.getElementById("interest").innerHTML = obj.interest  */
			document.getElementById("donut-text").innerHTML = 'S$' + obj.payment + ' /month'
			principalResult = Number(obj.principal)
			interestResult = Number(obj.interest)	
			
			getChart()	
		}
	})
}

function identifyErrors() {
	if (amt < 0 || amt == "") {
		document.getElementById("amt-error").innerHTML = "Loan amount must be more than $0"
	} else if (tenure < 0 || tenure > 35 || tenure == "") {
		document.getElementById("tenure-error").innerHTML = "Tenure must be between 1 and 35 years"
	} else if (interest < 0 || interest == ""){
		document.getElementById("int-error").innerHTML = "Interest rate must be more than 0"
	}
}

function getChart() {
	if (doughnutChart)
		doughnutChart.destroy();
	
	// set up 
	const data = {
			labels: [
	 			'Principal',
	    		'Interest'
	  		],
	  		datasets: [{
	    	label: 'Monthly Repayment ($)',
	    	data: [principalResult, interestResult],
		    backgroundColor: [
		    	'rgb(54, 162, 235)',
		      	'rgb(255, 205, 86)'
		    ],
		    hoverOffset: 4
  		}]
	};
	
	const config =  {
		type: 'doughnut',
	  	data: data,
	};
	
	doughnutChart = new Chart(document.getElementById("loan-canvas"), config)
}
