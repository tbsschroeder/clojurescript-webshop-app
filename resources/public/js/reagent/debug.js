// Compiled by ClojureScript 1.10.844 {}
goog.provide('reagent.debug');
goog.require('cljs.core');
reagent.debug.has_console = (typeof console !== 'undefined');
reagent.debug.tracking = false;
if((typeof reagent !== 'undefined') && (typeof reagent.debug !== 'undefined') && (typeof reagent.debug.warnings !== 'undefined')){
} else {
reagent.debug.warnings = cljs.core.atom.call(null,null);
}
if((typeof reagent !== 'undefined') && (typeof reagent.debug !== 'undefined') && (typeof reagent.debug.track_console !== 'undefined')){
} else {
reagent.debug.track_console = (function (){var o = ({});
(o.warn = (function() { 
var G__25271__delegate = function (args){
return cljs.core.swap_BANG_.call(null,reagent.debug.warnings,cljs.core.update_in,new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"warn","warn",-436710552)], null),cljs.core.conj,cljs.core.apply.call(null,cljs.core.str,args));
};
var G__25271 = function (var_args){
var args = null;
if (arguments.length > 0) {
var G__25272__i = 0, G__25272__a = new Array(arguments.length -  0);
while (G__25272__i < G__25272__a.length) {G__25272__a[G__25272__i] = arguments[G__25272__i + 0]; ++G__25272__i;}
  args = new cljs.core.IndexedSeq(G__25272__a,0,null);
} 
return G__25271__delegate.call(this,args);};
G__25271.cljs$lang$maxFixedArity = 0;
G__25271.cljs$lang$applyTo = (function (arglist__25273){
var args = cljs.core.seq(arglist__25273);
return G__25271__delegate(args);
});
G__25271.cljs$core$IFn$_invoke$arity$variadic = G__25271__delegate;
return G__25271;
})()
);

(o.error = (function() { 
var G__25274__delegate = function (args){
return cljs.core.swap_BANG_.call(null,reagent.debug.warnings,cljs.core.update_in,new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"error","error",-978969032)], null),cljs.core.conj,cljs.core.apply.call(null,cljs.core.str,args));
};
var G__25274 = function (var_args){
var args = null;
if (arguments.length > 0) {
var G__25275__i = 0, G__25275__a = new Array(arguments.length -  0);
while (G__25275__i < G__25275__a.length) {G__25275__a[G__25275__i] = arguments[G__25275__i + 0]; ++G__25275__i;}
  args = new cljs.core.IndexedSeq(G__25275__a,0,null);
} 
return G__25274__delegate.call(this,args);};
G__25274.cljs$lang$maxFixedArity = 0;
G__25274.cljs$lang$applyTo = (function (arglist__25276){
var args = cljs.core.seq(arglist__25276);
return G__25274__delegate(args);
});
G__25274.cljs$core$IFn$_invoke$arity$variadic = G__25274__delegate;
return G__25274;
})()
);

return o;
})();
}
reagent.debug.track_warnings = (function reagent$debug$track_warnings(f){
(reagent.debug.tracking = true);

cljs.core.reset_BANG_.call(null,reagent.debug.warnings,null);

f.call(null);

var warns = cljs.core.deref.call(null,reagent.debug.warnings);
cljs.core.reset_BANG_.call(null,reagent.debug.warnings,null);

(reagent.debug.tracking = false);

return warns;
});

//# sourceMappingURL=debug.js.map
