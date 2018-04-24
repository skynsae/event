function CurrencyFormatted(amount)
    {
        
        var q = amount.indexOf(",");
        
        if(q > 0){
            amount = amount.replace (/,/g, "");
        }
        
        var i = parseFloat(amount);
        
        if(isNaN(i)) { i = 0.00; }
        var minus = '';
        if(i < 0) { minus = '-'; }
        i = Math.abs(i);
        i = parseInt((i + .005) * 100);
        i = i / 100;
        s = new String(i);
        if(s.indexOf('.') < 0) { s += '.00'; }
        if(s.indexOf('.') == (s.length - 2)) { s += '0'; }
        s = minus + s;        
        return s;
    }

    function CommaFormatted(amount)
    {
        var delimiter = ","; // replace comma if desired
        var a = amount.split('.',2)
        var d = a[1];
        var i = parseInt(a[0]);
        if(isNaN(i)) { return ''; }
        var minus = '';
        if(i < 0) { minus = '-'; }
        i = Math.abs(i);
        var n = new String(i);
        var a = [];
        while(n.length > 3)
        {
            var nn = n.substr(n.length-3);
            a.unshift(nn);
            n = n.substr(0,n.length-3);
        }
        if(n.length > 0) { a.unshift(n); }
        n = a.join(delimiter);
        if(d.length < 1) { amount = n; }
        else { amount = n + '.' + d; }
        amount = minus + amount;
        return amount;
    }