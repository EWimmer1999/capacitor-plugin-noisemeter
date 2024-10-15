import { registerPlugin } from '@capacitor/core';

import type { NoiseMeterPlugin } from './definitions';

const NoiseMeter = registerPlugin<NoiseMeterPlugin>('NoiseMeter', {
  web: () => import('./web').then((m) => new m.NoiseMeterWeb()),
});

export * from './definitions';
export { NoiseMeter };
