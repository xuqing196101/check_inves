if($.validator){
    $.validator.prototype.elements = function () {
        var validator = this,
        rulesCache = {};
        // Select all valid inputs inside the form (no submit or reset buttons)
        return $( this.currentForm )
        .find( "input, select, textarea, [contenteditable]" )
        .not( ":submit, :reset, :image, :disabled" )
        .not( this.settings.ignore )
        .filter( function() {
            var name = this.id || this.name || $( this ).attr( "name" ); // For contenteditable
            if ( !name && validator.settings.debug && window.console ) {
                console.error( "%o has no name assigned", this );
            }
            // Set form expando on contenteditable
            if ( this.hasAttribute( "contenteditable" ) ) {
                this.form = $( this ).closest( "form" )[ 0 ];
            }
            // Select only the first element for each name, and only those with rules specified
            if (name in rulesCache || !validator.objectLength( $( this ).rules() ) ) {
                return false;
            }
            rulesCache[ name ] = true;
            return true;
        } );
    }
}