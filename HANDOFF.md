# HANDOFF — casehub-fsitrading

**Branch:** `7-chapter1-domain-baseline`  
**Issue:** #7 (OPEN)  
**Date:** 2026-06-30

## What's Done

Chapters 1–2 delivered on this branch:
- **C1** (`834faae`) — domain model, SPI layer, REST endpoints, synthetic execution pipeline. 33 tests.
- **C2** (`df38d9a`) — immutable audit trail with Merkle proofs. OrderExecutionLedgerEntry + StrategyEvaluationLedgerEntry. 51 tests total.
- **ARC42STORIES.MD** — 12-chapter roadmap written (`7074208`), C1+C2 entries populated.

## Uncommitted State

`ARC42STORIES.MD` — modified (carried from prior session). Check diff before committing.

## Immediate Next Step

Commit or discard ARC42STORIES.MD changes, then start Chapter 3 (Trust Scoring from P&L Attestations) — `OutcomeAttestationEntry`, nightly `TrustScoreJob`, Bayesian Beta per strategy.

## What's Next

| # | Description | Scale | Complexity | Notes |
|---|-------------|-------|------------|-------|
| — | C3: Trust Scoring from P&L Attestations | L | Med | L4 ledger; depends on C2 |
| — | C4: Trust-Weighted Strategy Selection | L | High | L6 trust routing; depends on C3 |
| — | C5: Market Data Stream Ingestion | L | High | L7 streams; independent of C3–C4 |

## References

- `docs/DOMAIN.md` — full domain background
- `ARC42STORIES.MD` — 12-chapter roadmap with layer taxonomy
- `../parent/docs/PLATFORM.md` — platform capability ownership
