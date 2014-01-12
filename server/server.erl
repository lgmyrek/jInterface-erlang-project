-module(server).
-export([start/0, loop_/0, state_/1, proxy_/0, update_/1]).

% REJESTRUJE WSZYSTKIE USŁUGI SERWERA
start() ->
	register(state,spawn(server, state_,[{state,{now(),[],[]}}])),
	register(update,spawn(server, update_,[[]])),
	register(loop,spawn(server,loop_,[])),
	register(proxy,spawn(server,proxy_,[])).

%%% GOTOWE %%%
% PRZETRZYMUJE STAN SERWERA
% MOZE ODEBRAC ZADANIE WYSLANIA AKTUALNEGO STANU LUB NADPISANIA GO NOWYM
state_(State) ->
	receive 
		{getState, PID} -> 
				NewState=State,
				PID ! State;
		{updateState, NewState} -> ok
	end,
	state_(NewState).

%%% GOTOWE %%%
% ODPOWIADA ZA AKTUALIZACJE STANU SERWERA
% SPRAWDZA CZY PORUSZYC JUZ POCISKIEM, PRZEMIESZCZA STATKI NA MAPIE, STRZELA
% JEZELI ZADZIALALA UAKTUALNIA STAN SERWERA
loop_() ->
	state ! {getState, self()},
	receive 
		State -> make_something_with_state
	end,
	{state, {Time, Ships, Fire}} = State,
	Time_now = now(),
	Time_condition = solver:check_time(Time, Time_now), %sprawdza czy minela sekunda
	if
		(Time_condition==true) -> %minela 1/60 sekunda, zmieniamy stan gry
			update ! getUpdate,
			receive 
				Update -> 
					State_ = solver:update_game(Time_now,Ships,Fire,Update),
					state ! {updateState, {state,State_}}
			end;
		true -> ok
	end,
	loop_().

%%% GOTOWE %%%
% KOMUNIKACJA Z SERWEREM, KTORY BEDZIE BOMBARDOWANY TU KOMUNIKACJA
proxy_() ->
	receive
		{left, ShipId} -> update ! {left, ShipId};
		{right, ShipId} -> update ! {right, ShipId};
		{forward, ShipId} -> update ! {forward, ShipId};
		{fire, ShipId} -> update ! {fire, ShipId};
		{getState, PID} -> 
			state ! {getState, self()},
			receive 
				State -> 
					PID ! State
			end;
		{newShip, ShipId} -> update ! {newShip, ShipId}
	end,
	proxy_().


%%% GOTOWE %%%
% JEZELI KOMUNIKACJA BYLA NIEPOWTARZALNA NA PRZESTRZENI WYKONANIA JEDNEGO LOOPA DOPISUJE NOWE AKCJE
update_(Update) ->
	receive
		{left, ShipId} -> Update_ = solver:add_move(Update, left, ShipId);
		{right, ShipId} -> Update_ = solver:add_move(Update, right, ShipId);
		{forward, ShipId} -> Update_ = solver:add_move(Update, forward, ShipId);
		{fire, ShipId} -> Update_ = solver:add_move(Update, fire, ShipId);
		{newShip, ShipId} -> Update_ = solver:add_ship(Update, ShipId);
		getUpdate -> 
			loop ! Update,
			Update_ = []
	end,
	update_(Update_).
	
