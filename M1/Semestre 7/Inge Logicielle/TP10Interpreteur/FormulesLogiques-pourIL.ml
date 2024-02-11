
type form =
  | Top | Bot
  | Var of string
  | Not of form
  | And of form * form
  | Or of form * form
  | Imp of form * form
  | Equ of form * form;; 

(* Q2 *)
(*Pour pouvoir ajouter le Xor, il faudrait modifier le type form ce qui est impossible sans modifier le code de A*)

let rec string_of_form = function
  | Top -> "true"
  | Bot -> "false"
  | Var n -> n
  | Not f -> "~" ^ (string_of_form f)
  | And (f1, f2) ->
      "(" ^ (string_of_form f1) ^ "/\\" ^ (string_of_form f2) ^ ")"
  | Or (f1, f2) ->
      "(" ^ (string_of_form f1) ^ "\\/" ^ (string_of_form f2) ^ ")"
  | Imp (f1, f2) ->
      "(" ^ (string_of_form f1) ^ "->" ^ (string_of_form f2) ^ ")"
  | Equ (f1, f2) ->
      "(" ^ (string_of_form f1) ^ "<->" ^ (string_of_form f2) ^ ")";;

let f = Imp (And (Var "A", Var "B"), Or (Not (Var "C"), Top));;
print_endline (string_of_form f);;

(* Q3 *)

let simplif_and = function
  | (f, Top) | (Top, f) -> f
  | (_, Bot) | (Bot, _) -> Bot
  | (l, r) -> And (l, r);;

let simplif_or = function
  | (_, Top) | (Top, _) -> Top
  | (f, Bot) | (Bot, f) -> f
  | (l, r) -> Or (l, r);;

let simplif_imp = function
  | (_, Top) | (Bot, _) -> Top
  | (f, Bot) -> Not f
  | (Top, f) -> f
  | (l, r) -> Imp (l, r);;

let simplif_equ = function
  | (f, Top) | (Top, f) -> f
  | (_, Bot) | (Bot, _) -> Bot
  | (l, r) -> Equ (l, r);;

let rec simplif_form = function
  | And (f1, f2) ->
      let f1' = simplif_form f1
      and f2' = simplif_form f2 in
      simplif_and (f1', f2')
  | Or (f1, f2) ->
      let f1' = simplif_form f1
      and f2' = simplif_form f2 in
      simplif_or (f1', f2')
  | Imp (f1, f2) ->
      let f1' = simplif_form f1
      and f2' = simplif_form f2 in
      simplif_imp (f1', f2')
  | Equ (f1, f2) ->
      let f1' = simplif_form f1
      and f2' = simplif_form f2 in
      simplif_equ (f1', f2')
  | f -> f;;

let f = And (Var "A", Or (Var "B", Top));;
let f' = simplif_form f;;
print_endline (string_of_form f);;
print_endline (string_of_form f');;

(* Q4 *)

(* pour calculer la valeur on utilise les fonctions logiques ocaml not, &&, || et = pour l'équivalence *)

let rec eval_form l = function
  | Top -> true
  | Bot -> false
  | Var n ->
      (try List.assoc n l
       with Not_found -> failwith (n ^ " not in the assignment!"))
  | Not f -> not (eval_form l f)
  | And (f1, f2) ->
      let f1' = eval_form l f1
      and f2' = eval_form l f2 in
      f1' && f2'
  | Or (f1, f2) ->
      let f1' = eval_form l f1
      and f2' = eval_form l f2 in
      f1' || f2'
  | Imp (f1, f2) ->
      let f1' = eval_form l f1
      and f2' = eval_form l f2 in
      (not f1') || f2'
  | Equ (f1, f2) ->
      let f1' = eval_form l f1
      and f2' = eval_form l f2 in
      f1' = f2';;

let f = Imp (Var "A", Imp (Var "B", Var "A"));;
let l = [("A", true); ("B", true)];;
eval_form l f;;

(* Q5 *)

(* le perturbant "in L2" signifie que le corps de let est l'expression l2,
donc que la valeur de l2 est rendue à l'appelant *)

(* get_vars définie par ailleurs *)
let rec get_vars l = [];;
(* fonction récursive prenant en arguments (1) une liste de strings l et (2) un élément f de type form, rendant la liste l augment´ee des noms des variables utilis´ees dans f *)
let rec get_vars l = function
  | Var n -> if not(List.mem n l) then l @ [n] else l
  | Not f -> get_vars l f
  | And (f1, f2) ->
      let l1 = get_vars l f1 in
      let l2 = get_vars l1 f2 in l2 
  | Or (f1, f2) ->
      let l1 = get_vars l f1 in
      let l2 = get_vars l1 f2 in l2
  | Imp (f1, f2) ->
      let l1 = get_vars l f1 in
      let l2 = get_vars l1 f2 in l2
  | Equ (f1, f2) ->
      let l1 = get_vars l f1 in
      let l2 = get_vars l1 f2 in l2
  | _ -> l;;

(* is_tauto est au final une fonction ayant 3 paramètres, f, asg et une form sur laquelle s'effectue le matching *)

let rec is_tauto f asg = function
  | [] -> eval_form asg f
  | v :: tl ->
      (is_tauto f (asg @ [(v, true)]) tl) &&
      (is_tauto f (asg @ [(v, false)]) tl);;

let f = Imp (Var "A", Imp (Var "B", Var "A"));;
let l = get_vars [] f;;
is_tauto f [] l;;
let f = Imp (Var "A", Imp (Var "B", Var "C"));;
let l = get_vars [] f;;
is_tauto f [] l;;
