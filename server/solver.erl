-module(solver).
-export([add_move/3, check_time/2, move_fire/2, move_ship/1, update_ship_and_fire/4, 
	check_hit/3, update_game/4, add_fire/3, add_action/2, add_ship/2]).

add_move(Update, Move, ShipID) ->
	Others = [{Ship, Moves} || {Ship, Moves} <- Update, Ship /= ShipID],
	ShipsID = [Ship || {Ship,_} <- Update],
	Condition0 = lists:member(ShipID, ShipsID),
	if 
		Condition0 == true ->
			This = [{Ship, Moves} || {Ship, Moves} <- Update, Ship == ShipID],
			[{Ship, Moves}] = This;
		true ->
			Moves = [],
			Ship = ShipID
	end,
	Condition = lists:member(Move,Moves),
	if
		(Condition==false) ->
			New_Data = {ShipID, [Move|Moves]};
		true -> New_Data = {ShipID, Moves}
	end,
	[New_Data|Others].

check_time({_,Sec,MSec}, {_, Sec_, MSec_}) ->
	Time = Sec*1000000+MSec,
	Time_ = Sec_*1000000+MSec_,
	if
		Time_-Time>16666 -> true;
		true -> false
	end.

%%% Dodanie nowego statku
add_ship(Update, ShipId) ->
	[{ShipId,[]}|Update].


%%% Wykonanie update serweru %%%
update_game(Time,Ships,Fire,Update) ->
	Ships_v0 = check_new(Ships,[ShipId || {ShipId, _} <- Update]),
	{Ships_v1, Fire_v1} = update_ship_and_fire(Ships_v0,Update,Fire,[]),
	Fire_v2 = move_fire(Fire_v1,[]),
	Ships_v2 = check_hit(Ships_v1,Fire_v2,[]),
	{Time,Ships_v2,Fire_v2}.
	
%%% Sprawdza czy do gry nie wlaczyly sie nowe statki
check_new(Ships,[]) -> Ships;
check_new(Ships,[Ship|Tail]) ->
	Ship_list = [ShipId || {_,_,ShipId,_} <- Ships],
	Condition = lists:member(Ship,Ship_list),
	if
		Condition==false -> 
			check_new([{0.0,{random:uniform()*800.0,random:uniform()*600.0},Ship,false}|Ships],Tail);
		true -> check_new(Ships,Tail)
	end.
	

%%% Dodaje wystrzaly oraz porusza statkiem %%% OK
update_ship_and_fire([],_,Fire,New_ShipState) -> 
	{New_ShipState, Fire};
update_ship_and_fire([Ship|STail], Update, Fire, New_ShipState) -> 
	{Eagle, {X,Y}, ShipId, _} = Ship, %dane statku
	Moves_ = [Moves|| {SiD, Moves}<-Update, SiD==ShipId], %wyciagam update dla tego statku
	Moves = lists:flatten(Moves_), % splaczczam, bo jezeli update nie pusty to jest to lista w liscie
	Fire_Condition = lists:member(fire,Moves), %czy statek wystrzelil
	if
		Fire_Condition == true ->
			Fire_ = add_fire(Eagle,{X,Y},Fire); %dodaje strzal
		true ->
			Fire_ = Fire
	end,
	Ship_ = add_action(Ship, Moves), %dodaje wszystkie ruchy
	update_ship_and_fire(STail, Update, Fire_, [Ship_|New_ShipState]). %przechodzi dalej

%%% Proste dodawanie nowego strzalu %%% OK	
add_fire(Eagle, {X_,Y_}, Fire) ->
	X = X_ + 42 * math:cos(math:pi() * Eagle/180.0),
	Y = Y_ + 42 * math:sin(math:pi() * Eagle/180.0),
	[{Eagle,{X,Y}}|Fire]. 

%%% Obsluga poruszania statkiem %%% OK
add_action(Ship, []) -> Ship;
add_action(Ship, [Move|MTail]) ->
	ES = 2.0, % o ile obracac kat w ciagu jednego przejscia petli
	{Eagle, {X,Y}, ShipId, IsDead} = Ship,
	if
		Move == left ->
			Eagle_ = Eagle + ES;
		Move == right -> 
			Eagle_ = Eagle - ES;
		true -> Eagle_ = Eagle
	end,
	if
		Move == forward ->
			Ship_ = move_ship({Eagle_, {X,Y}, ShipId, IsDead}); % poruszenie statka do przodu
		true ->
			Ship_ = {Eagle_, {X,Y}, ShipId, IsDead}
	end,
	add_action(Ship_,MTail).

%%% Iteruje po kazdym statku i sprawdza czy nie zostal trafiony %%%
check_hit([], Fire, New_ShipList) ->
	New_ShipList; % zwraca zaktualizowana liste statkow
check_hit([Ship|STail], Fire, New_ShipList) -> % pierwsza iteracja New_ShipList = []
	IsDead = try_fire(Ship, Fire), % dla kazdego statku pisze czy jest martwy czy nie
	if 
		IsDead == true -> 
			check_hit(STail, Fire, New_ShipList);
		true -> 
			check_hit(STail, Fire, [Ship|New_ShipList])		
	end.

try_fire(_, []) -> false;
try_fire(Ship, [{_, {F_X, F_Y}}|Tail]) ->
	{_, {X,Y}, _, _} = Ship,
	X_RES = 41.0, % wymiary statku,
	Y_RES = 41.0,
	Distance_Cond = math:sqrt(math:pow(X-F_X,2)+math:pow(Y-F_Y,2)),
	if 
		X_RES>Distance_Cond -> 
			Dead = true;
		true -> Dead = try_fire(Ship, Tail)
	end,
	Dead.

move_fire([],FireList) -> FireList;
move_fire([{Eagle, {X, Y}}|FireList],New_FireList) -> %Nowe wspolrzedne pocisku
	PF = 5.0, % ile pikseli do przodu pocisk
	X_ = X + PF * math:cos(math:pi() * Eagle/180.0),
	Y_ = Y + PF * math:sin(math:pi() * Eagle/180.0),
	if 
		X_ <0.0 -> move_fire(FireList,New_FireList);
		X_>800.0 -> move_fire(FireList,New_FireList);
		Y_ <0.0 -> move_fire(FireList,New_FireList);
		Y_>600.0 -> move_fire(FireList,New_FireList);
		true -> move_fire(FireList,[{Eagle, {X_, Y_}}|New_FireList])
	end.

move_ship({Eagle, {X, Y}, ShipId, IsDead}) -> %Nowe wspolrzedne statku
	PF = 3.0, % ile pikseli do przodu statek
	X_ = X + PF * math:cos(math:pi() * Eagle/180.0),
	Y_ = Y + PF * math:sin(math:pi() * Eagle/180.0),
	if 
		X_ <0.0 -> New_X = 0.0;
		X_>800.0 -> New_X = 800.0;
		true -> New_X = X_
	end,
	if
		Y_ <0.0 -> New_Y = 0.0;
		Y_>600.0 -> New_Y = 600.0;
		true -> New_Y = Y_
	end,
	{Eagle, {New_X, New_Y}, ShipId, IsDead}.
