function SuggestionProvider() {
    //any initializations needed go here
    this.suggestURL = "http://localhost:1448/eBay/suggest?query=";
}

SuggestionProvider.prototype.requestSuggestions = function (oAutoSuggestControl, bTypeAhead) {

	var request = new XMLHttpRequest();
    var sTextboxValue = oAutoSuggestControl.textbox.value;

    request.open("GET", this.suggestURL + encodeURI(sTextboxValue));
    request.onreadystatechange = function() {
    	var aSuggestions = [];
    	if (request.readyState === 4 && request.status === 200) {
            if (oAutoSuggestControl.textbox.value) {
                var s = request.responseXML.getElementsByTagName('CompleteSuggestion');
                for (var i = 0; i < s.length; i++) {
                    aSuggestions.push(s[i].childNodes[0].getAttribute("data"));
                }  
            }
    	}
   		oAutoSuggestControl.autosuggest(aSuggestions, bTypeAhead);
    };
    request.send(null);
};